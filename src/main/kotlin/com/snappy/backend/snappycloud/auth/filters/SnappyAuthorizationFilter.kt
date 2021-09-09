package com.snappy.backend.snappycloud.auth.filters

import com.snappy.backend.snappycloud.auth.TokenSnappy
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SnappyAuthorizationFilter(private val tokenUtils: TokenSnappy) : OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain,
    ) {
        if ((request.servletPath == "/api/login")
                || (request.servletPath == "/api/token/refresh")
                || (request.servletPath == "/api/user/update/password")
                || (request.servletPath == "/api/user/delete")) {
            filterChain.doFilter(request, response)
        } else {
            try {
                val businessId = request.getParameter("business")
                if (businessId == null)
                    tokenUtils.sendErrorResponse(response, "Business ID was not specified")

                tokenUtils.validateUser(request, businessId)
                filterChain.doFilter(request, response)
            } catch (ex: Exception) {
                tokenUtils.sendErrorResponse(response, ex.message ?: "Something went wrong.")
            }
        }
    }
}