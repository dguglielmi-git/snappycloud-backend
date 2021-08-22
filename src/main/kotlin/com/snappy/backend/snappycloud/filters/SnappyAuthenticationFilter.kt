package com.snappy.backend.snappycloud.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.snappy.backend.snappycloud.services.UserBusinessRoleService
import com.snappy.backend.snappycloud.services.UserService
import com.snappy.backend.snappycloud.utils.TokenUtils
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class SnappyAuthenticationFilter(
        authenticationManager: AuthenticationManager,
        private val userService: UserService,
        private val userBusinessRoleService: UserBusinessRoleService,
) : UsernamePasswordAuthenticationFilter(authenticationManager) {
    private val tokenUtils = TokenUtils()

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
        val username: String = request.getParameter("username")
        val password: String = request.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain,
            authentication: Authentication,
    ) {
        val url: String = request.requestURL.toString()
        tokenUtils.service = userService
        tokenUtils.userBusinessRoleService = userBusinessRoleService
        val tokens: MutableMap<String, String> = tokenUtils.getTokens(authentication, url)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, tokens)
    }
}