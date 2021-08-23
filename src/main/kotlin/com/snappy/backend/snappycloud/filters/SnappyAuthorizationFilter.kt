package com.snappy.backend.snappycloud.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.snappy.backend.snappycloud.utils.TokenUtils
import com.snappy.backend.snappycloud.utils.UsersLogged
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SnappyAuthorizationFilter() : OncePerRequestFilter() {

    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain,
    ) {
        if ((request.servletPath == "/api/login") || (request.servletPath == "/api/token/refresh")) {
            filterChain.doFilter(request, response)
        } else {
            val authorizationHeader: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                this.response = response
                this.request = request
                try {
                    val tokenUtils = TokenUtils()
                    val username = tokenUtils.getUserFromToken(authorizationHeader)
                    val businessId = request.getParameter("business")
                    if (businessId == null) showError("Business ID was not specified")
                    val authorities = getAuthorities(username, businessId)
                    val authenticationToken =
                            UsernamePasswordAuthenticationToken(username, null, authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (ex: Exception) {
                    showError(ex.message ?: "Something went wrong.")
                }
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }

    private fun getAuthorities(username: String, businessId: String) : MutableList<SimpleGrantedAuthority> =
            UsersLogged.getProfilesAuthorities(username,businessId.toLong())

    private fun showError(errorMsg: String) {
        this.response.setHeader("error", errorMsg)
        this.response.status = HttpStatus.FORBIDDEN.value()
        val error = mutableMapOf<String, String?>()
        error.put("error_message", errorMsg)
        this.response.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(this.response.outputStream, error)
    }
}