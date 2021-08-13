package com.snappy.backend.snappycloud.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.services.UserService
import com.snappy.backend.snappycloud.utils.TokenUtils
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.lang.RuntimeException
import java.net.URI
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api")
class UserController(
    private val userService: UserService,
) {
    private val tokenUtils = TokenUtils()

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<User>> = ResponseEntity.ok().body(userService.findAll())

    @PostMapping("/user/save")
    fun saveUser(@RequestBody user: User): ResponseEntity<User> {
        val uri: URI =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString())
        return ResponseEntity.created(uri).body(userService.save(user))
    }

    @Throws(IOException::class)
    @GetMapping("/token/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authorizationHeader: String? = request.getHeader(AUTHORIZATION)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                val algorithm: Algorithm = Algorithm.HMAC256("secret".toByteArray())

                val refresh_token: String = authorizationHeader.substring("Bearer ".length)

                val verifier: JWTVerifier = JWT.require(algorithm).build()
                val decodedJWT: DecodedJWT = verifier.verify(refresh_token)
                val username: String = decodedJWT.subject
                val user: User? = userService.findByUsername(username)

                val access_token: String = JWT.create()
                    .withSubject(user?.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.requestURL.toString())
                    .withClaim("profiles", user?.profiles)
                    .sign(algorithm)

                val tokens = mutableMapOf<String, String>()
                tokens.put("access_token", access_token)
                tokens.put("refresh_token", refresh_token)
                response.contentType = APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, tokens)
            } catch (ex: Exception) {
                response.setHeader("error", ex.message)
                response.status = HttpStatus.FORBIDDEN.value()
                val error = mutableMapOf<String, String?>()
                error.put("error_message", ex.message)
                response.contentType = APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, error)
            }
        } else {
            throw RuntimeException("Refresh token is missing")
        }
    }


}