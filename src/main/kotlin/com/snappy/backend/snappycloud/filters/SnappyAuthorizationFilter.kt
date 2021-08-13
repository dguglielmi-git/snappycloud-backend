package com.snappy.backend.snappycloud.filters

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.fasterxml.jackson.databind.ObjectMapper
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

class SnappyAuthorizationFilter : OncePerRequestFilter() {
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
                try {
                    val token: String = authorizationHeader.substring("Bearer ".length)
                    val algorithm: Algorithm = Algorithm.HMAC256("secret".toByteArray())
                    val verifier: JWTVerifier = JWT.require(algorithm).build()
                    val decodedJWT: DecodedJWT = verifier.verify(token)
                    val username: String = decodedJWT.subject
                    val profiles = decodedJWT.getClaim("profiles").asArray(String::class.java).toMutableList()
                    val authorities = mutableListOf<SimpleGrantedAuthority>()
                    profiles.forEach { profile -> authorities.add(SimpleGrantedAuthority(profile)) }
                    val authenticationToken: UsernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(username, null, authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (ex: Exception) {
                    response.setHeader("error", ex.message)
                    response.status = HttpStatus.FORBIDDEN.value()
                    val error = mutableMapOf<String, String?>()
                    error.put("error_message",ex.message)
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream,error)
                }
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }
}