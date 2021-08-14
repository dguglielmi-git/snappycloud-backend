package com.snappy.backend.snappycloud.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.snappy.backend.snappycloud.services.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*
import java.util.stream.Collectors

class TokenUtils {
    lateinit var service: UserService
    private val algorithm: Algorithm = Algorithm.HMAC256(System.getenv("SECRET_SNAPPY").toByteArray())

    fun getTokens(authentication: Authentication, requestURL: String): MutableMap<String, String> {
        val user: User = authentication.principal as User
        val accessToken: String = getAccessToken(user, requestURL)
        val refreshToken: String = getRefreshToken(user, requestURL)

        val tokens = mutableMapOf<String, String>()
        tokens["access_token"] = accessToken
        tokens["refresh_token"] = refreshToken
        return tokens
    }

    fun getRefreshToken(
        authHeader: String,
        url: String,
    ): MutableMap<String, String> {
        val refresh_token: String = authHeader.substring("Bearer ".length)
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(refresh_token)
        val username: String = decodedJWT.subject
        val user: com.snappy.backend.snappycloud.models.User? = service.findByUsername(username)

        val access_token: String = JWT.create()
            .withSubject(user?.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .withIssuer(url)
            .withClaim("profiles", user?.profiles)
            .sign(algorithm)

        val tokens = mutableMapOf<String, String>()
        tokens["access_token"] = access_token
        tokens["refresh_token"] = refresh_token
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