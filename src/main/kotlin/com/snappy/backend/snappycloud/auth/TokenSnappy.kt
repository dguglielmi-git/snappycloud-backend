package com.snappy.backend.snappycloud.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.snappy.backend.snappycloud.dtos.TokenDTO
import com.snappy.backend.snappycloud.models.UserBusinessRole
import com.snappy.backend.snappycloud.services.UserBusinessRoleService
import com.snappy.backend.snappycloud.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletResponse

@Component
class TokenSnappy(
        private val service: UserService,
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
            val accessToken: String = getAccessToken(user.username, requestURL)
            val refreshToken: String = getRefreshToken(user.username, requestURL)
            val tokens = mutableMapOf<String, String>()
            tokens["access_token"] = accessToken
            tokens["refresh_token"] = refreshToken
            sendResponse(response, tokens)
        } catch (ex: Exception) {
            sendErrorResponse(response, ex.message ?: "Something went wrong")
        }
    }

    fun sendRefreshTokens(
            authHeader: String,
            url: String, response: HttpServletResponse
    ) {
        try {
            val username: String = getUserFromToken(authHeader)
            val user: com.snappy.backend.snappycloud.models.User? = service.findByUsername(username)
            val refreshToken: String = authHeader.substring("Bearer ".length)
            val accessToken: String = if (user != null) getAccessToken(user.username, url) else ""

            val tokens = mutableMapOf<String, String>()
            tokens["access_token"] = accessToken
            tokens["refresh_token"] = refreshToken
            val tokensResponse = TokenDTO(
                    tokens["access_token"] ?: "not access token found",
                    tokens["refresh_token"] ?: "not refresh token found")
            sendResponse(response, tokensResponse)
        } catch (ex: Exception) {
            sendErrorResponse(response, ex.message ?: "Something went wrong")
        }
    }

    fun validateUser(authorizationHeader: String, businessId: String) {
        val username = getUserFromToken(authorizationHeader)
        val authorities = getAuthorities(username, businessId)
        val authenticationToken =
                UsernamePasswordAuthenticationToken(username, null, authorities)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    fun getUserFromToken(authHeader: String): String {
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(authHeader.substring("Bearer ".length))
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

    private fun loadUserProfiles(user: org.springframework.security.core.userdetails.User) {
        val userdetail = service.findByUsername(user.username)
        if (userdetail != null) {
            val profiles: Array<UserBusinessRole>? =
                    userBusinessRoleService.findByUser(userdetail.id)
            if (profiles != null) {
                UserLogged.getSession().removeUser(userdetail.username)
                profiles.forEach { prof ->
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