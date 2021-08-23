package com.snappy.backend.snappycloud.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.snappy.backend.snappycloud.models.UserBusinessRole
import com.snappy.backend.snappycloud.services.UserBusinessRoleService
import com.snappy.backend.snappycloud.services.UserService
import org.springframework.security.core.Authentication
import java.util.*

class TokenUtils {
    lateinit var service: UserService
    lateinit var authentication: Authentication
    lateinit var userBusinessRoleService: UserBusinessRoleService
    private val algorithm: Algorithm = Algorithm.HMAC256(System.getenv("SECRET_SNAPPY").toByteArray())

    fun getRefreshTokens(
            authHeader: String,
            url: String,
    ): MutableMap<String, String> {
        val username: String = getUserFromToken(authHeader)
        val user: com.snappy.backend.snappycloud.models.User? = service.findByUsername(username)
        val refreshToken: String = authHeader.substring("Bearer ".length)
        val accessToken: String = if (user != null) getAccessToken(user.username, url) else ""

        val tokens = mutableMapOf<String, String>()
        tokens.put("access_token", accessToken)
        tokens.put("refresh_token", refreshToken)
        return tokens
    }

    fun getTokens(requestURL: String): MutableMap<String, String> {
        val user: org.springframework.security.core.userdetails.User =
                authentication.principal as org.springframework.security.core.userdetails.User
        loadUserProfiles(user)
        val accessToken: String = getAccessToken(user.username, requestURL)
        val refreshToken: String = getRefreshToken(user.username, requestURL)
        val tokens = mutableMapOf<String, String>()
        tokens.put("access_token", accessToken)
        tokens.put("refresh_token", refreshToken)
        return tokens
    }

    fun getUserFromToken(authHeader: String): String {
        val tokenBearer: String = authHeader.substring("Bearer ".length)
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(tokenBearer)
        val username: String = decodedJWT.subject
        return username
    }

    private fun loadUserProfiles(user: org.springframework.security.core.userdetails.User) {
        val userdetail = service.findByUsername(user.username)
        if (userdetail != null) {
            val profiles: Array<UserBusinessRole>? = userBusinessRoleService.findByUser(userdetail.id)
            if (profiles != null) {
                UsersLogged.removeUser(userdetail.username)
                profiles.forEach { prof ->
                    UsersLogged.addProfile(userdetail.username, prof.profile.name, prof.business.id)
                }
            }
        }
    }

    private fun getAccessToken(user: String, requestURL: String): String =
            JWT.create()
                    .withSubject(user)
                    .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(requestURL)
                    .sign(algorithm)

    private fun getRefreshToken(user: String, requestURL: String): String =
            JWT.create()
                    .withSubject(user)
                    .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000))
                    .withIssuer(requestURL)
                    .sign(algorithm)
}