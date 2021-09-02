package com.snappy.backend.snappycloud.auth.filters

import com.snappy.backend.snappycloud.auth.TokenSnappy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class SnappyAuthenticationFilter(
        authenticationManager: AuthenticationManager,
        private val tokenUtils: TokenSnappy,
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

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
        tokenUtils.sendTokens(url, response, authentication)
    }
}