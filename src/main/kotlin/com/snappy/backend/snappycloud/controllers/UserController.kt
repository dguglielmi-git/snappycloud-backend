package com.snappy.backend.snappycloud.controllers

import com.snappy.backend.snappycloud.auth.TokenSnappy
import com.snappy.backend.snappycloud.dtos.MessageDTO
import com.snappy.backend.snappycloud.dtos.UserDTO
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.services.UserService
import com.snappy.backend.snappycloud.utils.Common
import org.modelmapper.ModelMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
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
        private val common: Common
) {
    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<UserDTO>> = ResponseEntity.ok().body(userService.findAll())

    @GetMapping("/user/{id}")
    fun getUserById(@PathVariable id: Long, response: HttpServletResponse):
            ResponseEntity<UserDTO?> {
        return try {
            ResponseEntity.ok().body(userService.getById(id))
        } catch (ex: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/user/save")
    fun saveUser(@RequestBody user: User): ResponseEntity<UserDTO> {
        val uri: URI =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/user/save").toUriString())
        return ResponseEntity.created(uri).body(userService.save(user))
    }

    @PutMapping("/user/update")
    fun updateUser(@RequestBody user: UserDTO, response: HttpServletResponse):
            ResponseEntity<UserDTO> {
        return try {
            val modelMapper = ModelMapper()
            val userParser = modelMapper.map(user, User::class.java)
            userParser.issueDate = common.dateStringToCalendar(user.issueDateString!!)

            ResponseEntity.ok().body(userService.update(userParser))
        } catch (ex: Exception) {
            tokenUtils.sendErrorResponse(response, "Error $ex")
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/user/update/password")
    fun updatePassword(request: HttpServletRequest): ResponseEntity<MessageDTO> {
        try {
            val username = tokenUtils.getUserFromToken(getBearer(request))
            val password = request.getParameter("password")
            userService.updateUserPassword(username, password)
            return ResponseEntity.ok().body(MessageDTO("User password updated."))
        } catch (ex: Exception) {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/user/delete")
    fun deleteUser(request: HttpServletRequest): ResponseEntity<MessageDTO> {
        try {
            val userOnToken = tokenUtils.getUserFromToken(getBearer(request))

            userService.findByUsername(userOnToken)?.let {
                it.active = 0
                userService.update(it)
                return ResponseEntity.ok().body(MessageDTO("User has been disabled."))
            }
        } catch (ex: Exception) {
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

    @Throws(IOException::class)
    @GetMapping("/token/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            request.requestURL.toString().let {
                tokenUtils.sendRefreshTokens(getBearer(request), it, response)
            }
        } catch (ex: Exception) {
            tokenUtils.sendErrorResponse(response, ex.message ?: "Something went wrong")
        }
    }

    private fun getBearer(request: HttpServletRequest): String {
        val authorizationHeader: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            authorizationHeader
        else ""
    }
}