package com.snappy.backend.snappycloud.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*
import java.util.stream.Collectors

class TokenUtils {

    private val algorithm: Algorithm = Algorithm.HMAC256("secret".toByteArray())

    fun getTokens(authentication: Authentication, requestURL: String): MutableMap<String, String> {
        val user: User = authentication.principal as User
        val accessToken: String = getAccessToken(user, requestURL)
        val refreshToken: String = getRefreshToken(user,requestURL)

        val tokens = mutableMapOf<String, String>()
        tokens["access_token"] = accessToken
        tokens["refresh_token"] = refreshToken
        return tokens
    }

    private fun getAccessToken(user: User, requestURL: String): String = JWT.create()
        .withSubject(user.username)
        .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
        .withIssuer(requestURL)
        .withClaim("profiles",
            user.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .sign(algorithm)

    private fun getRefreshToken(user: User, requestURL: String): String = JWT.create()
        .withSubject(user.username)
        .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000))
        .withIssuer(requestURL)
        .sign(algorithm)
}