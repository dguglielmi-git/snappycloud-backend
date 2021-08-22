package com.snappy.backend.snappycloud.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.snappy.backend.snappycloud.models.UserBusinessRole
import com.snappy.backend.snappycloud.services.UserBusinessRoleService
import com.snappy.backend.snappycloud.services.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import java.util.*
import java.util.stream.Collectors

class TokenUtils {
    lateinit var service: UserService
    lateinit var userBusinessRoleService: UserBusinessRoleService
    private val algorithm: Algorithm = Algorithm.HMAC256(System.getenv("SECRET_SNAPPY").toByteArray())

    fun getRefreshTokens(
            authHeader: String,
            url: String,
    ): MutableMap<String, String> {
        val refreshToken: String = authHeader.substring("Bearer ".length)
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(refreshToken)
        val username: String = decodedJWT.subject
        val user: com.snappy.backend.snappycloud.models.User? = service.findByUsername(username)

        val accessToken: String = JWT.create()
                .withSubject(user?.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(url)
//            .withClaim(
//                "profiles",
//                user?.profiles?.stream()?.map(Profile::name)?.collect(Collectors.toList())
//            )
                .sign(algorithm)

        val tokens = mutableMapOf<String, String>()
        tokens.put("access_token", accessToken)
        tokens.put("refresh_token", refreshToken)
        return tokens

    }

    fun getTokens(authentication: Authentication, requestURL: String): MutableMap<String, String> {
        val user: org.springframework.security.core.userdetails.User =
                authentication.principal as org.springframework.security.core.userdetails.User

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
        val accessToken: String = getAccessToken(user, requestURL)
        val refreshToken: String = getRefreshToken(user, requestURL)
        val tokens = mutableMapOf<String, String>()
        tokens.put("access_token", accessToken)
        tokens.put("refresh_token", refreshToken)
        return tokens
    }

    private fun getAccessToken(user: org.springframework.security.core.userdetails.User, requestURL: String): String =
            JWT.create()
                    .withSubject(user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(requestURL)
//                    .withClaim("profiles",
//                            user.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm)

    private fun getRefreshToken(user: org.springframework.security.core.userdetails.User, requestURL: String): String =
            JWT.create()
                    .withSubject(user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000))
                    .withIssuer(requestURL)
                    .sign(algorithm)
}