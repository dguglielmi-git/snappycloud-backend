package com.snappy.backend.snappycloud.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.services.UserService
import com.snappy.backend.snappycloud.utils.TokenUtils
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.lang.RuntimeException
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api")
class UserController(
        private val userService: UserService,
) {
    val tokenUtils = TokenUtils()

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
                tokenUtils.service = userService
                val url: String = request.requestURL.toString()
                val tokens = tokenUtils.getRefreshTokens(authorizationHeader, url)
                sendResponse(response, tokens)
            } catch (ex: Exception) {
                response.setHeader("error", ex.message)
                response.status = HttpStatus.FORBIDDEN.value()
                val error = mutableMapOf<String, String>()
                error.put("error_message", ex.message ?: "Error getting refresh token.")
                sendResponse(response, error)
            }
        } else {
            throw RuntimeException("Refresh token is missing")
        }
    }

    private fun sendResponse(response: HttpServletResponse, map: MutableMap<String, String>) {
        response.contentType = APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, map)
    }


}