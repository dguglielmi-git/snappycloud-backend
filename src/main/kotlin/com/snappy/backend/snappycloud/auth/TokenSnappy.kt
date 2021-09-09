package com.snappy.backend.snappycloud.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.snappy.backend.snappycloud.dtos.TokenDTO
import com.snappy.backend.snappycloud.services.UserBusinessRoleService
import com.snappy.backend.snappycloud.services.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenSnappy(
        private val userService: UserService,
        private val userBusinessRoleService: UserBusinessRoleService,
) {
    private val timeoutToken = 24 * 3600 * 1000
    private val timeoutRefreshToken = 30 * 60 * 1000
    private val algorithm: Algorithm =
            Algorithm.HMAC256(System.getenv("SECRET_SNAPPY").toByteArray())

    fun sendTokens(requestURL: String,
                   response: HttpServletResponse,
                   authentication: Authentication) {
        try {
            val user: org.springframework.security.core.userdetails.User =
                    authentication.principal as org.springframework.security.core.userdetails.User
            loadUserProfiles(user)
            sendResponse(response, getTokenAndRefreshToken(user.username, requestURL))
        } catch (ex: Exception) {
            sendErrorResponse(response, ex.message ?: "Something went wrong")
        }
    }

    private fun getTokenAndRefreshToken(user: String, url: String): TokenDTO =
            TokenDTO(getAccessToken(user, url), getRefreshToken(user, url))

    fun sendRefreshTokens(
            request: HttpServletRequest,
            url: String, response: HttpServletResponse
    ) {
        try {
            val username: String = getUserFromToken(request)
            val user: com.snappy.backend.snappycloud.models.User? = userService.findByUsername(username)
            val refreshToken: String = getBearer(request).substring("Bearer ".length)
            val accessToken: String = if (user != null) getAccessToken(user.username, url) else ""

            val tokensResponse = TokenDTO(accessToken, refreshToken)
            sendResponse(response, tokensResponse)
        } catch (ex: Exception) {
            sendErrorResponse(response, ex.message ?: "Something went wrong")
        }
    }

    fun validateUser(request: HttpServletRequest, businessId: String) {
        val username = getUserFromToken(request)
        val authorities = getAuthorities(username, businessId)
        val authenticationToken =
                UsernamePasswordAuthenticationToken(username, null, authorities)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    fun getUserFromToken(request: HttpServletRequest): String {
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(getBearer(request).substring("Bearer ".length))
        return decodedJWT.subject
    }

    fun getAuthorities(username: String, businessId: String): MutableList<SimpleGrantedAuthority> =
            UserLogged.getSession().getProfilesAuthorities(username, businessId.toLong())

    fun sendResponse(response: HttpServletResponse, msg: Any) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, msg)
    }

    fun sendErrorResponse(response: HttpServletResponse, errorMsg: String) {
        response.setHeader("error", errorMsg)
        response.status = HttpStatus.FORBIDDEN.value()
        val error = mutableMapOf<String, String?>()
        error["error_message"] = errorMsg
        sendResponse(response, error)
    }

    private fun getBearer(request: HttpServletRequest): String {
        val authorizationHeader: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            authorizationHeader
        else ""
    }

    private fun loadUserProfiles(user: org.springframework.security.core.userdetails.User) {
        userService.findByUsername(user.username)?.let { userdetail ->
            userBusinessRoleService.findByUser(userdetail.id)?.let {
                UserLogged.getSession().removeUser(userdetail.username)
                it.forEach { prof ->
                    UserLogged.getSession().addProfile(
                            userdetail.username,
                            prof.profile.name,
                            prof.business.id)
                }
            }
        }
    }

    private fun getAccessToken(user: String, requestURL: String): String =
            JWT.create()
                    .withSubject(user)
                    .withExpiresAt(Date(System.currentTimeMillis() + timeoutToken))
                    .withIssuer(requestURL)
                    .sign(algorithm)

    private fun getRefreshToken(user: String, requestURL: String): String =
            JWT.create()
                    .withSubject(user)
                    .withExpiresAt(Date(System.currentTimeMillis() + timeoutRefreshToken))
                    .withIssuer(requestURL)
                    .sign(algorithm)
}