package com.snappy.backend.snappycloud.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.snappy.backend.snappycloud.auth.TokenSnappy
import com.snappy.backend.snappycloud.dtos.TokenDTO
import com.snappy.backend.snappycloud.dtos.UserDTO
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.services.UserService
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api")
class UserController(
        private val userService: UserService,
        private val tokenUtils: TokenSnappy,
) {

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<UserDTO>> = ResponseEntity.ok().body(userService.findAll())

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
                val url: String = request.requestURL.toString()
                val tokens = tokenUtils.getRefreshTokens(authorizationHeader, url)
                val tokensResponse = TokenDTO(
                        tokens.get("access_token") ?: "not access token found",
                        tokens.get("refresh_token") ?: "not refresh token found")
                sendResponse(response, tokensResponse)
            } catch (ex: Exception) {
                sendErrorMessage(response, ex.message)
            }
        } else {
            throw RuntimeException("Refresh token is missing")
        }
    }

    private fun sendResponse(response: HttpServletResponse, valueResponse: Any) {
        response.contentType = APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, valueResponse)
    }

    private fun sendErrorMessage(response: HttpServletResponse, errorMessage: String?) {
        response.setHeader("error", errorMessage)
        response.status = HttpStatus.FORBIDDEN.value()
        val error = mutableMapOf<String, String>()
        error.put("error_message", errorMessage ?: "Error getting refresh token.")
        sendResponse(response, error)
    }
}