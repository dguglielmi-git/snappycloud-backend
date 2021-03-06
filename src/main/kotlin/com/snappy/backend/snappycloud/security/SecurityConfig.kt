package com.snappy.backend.snappycloud.security

import com.snappy.backend.snappycloud.auth.TokenSnappy
import com.snappy.backend.snappycloud.auth.filters.SnappyAuthenticationFilter
import com.snappy.backend.snappycloud.auth.filters.SnappyAuthorizationFilter
import com.snappy.backend.snappycloud.constants.SnappyConst
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
        val userDetailsService: UserDetailsService,
        val tokenUtils: TokenSnappy,
) : WebSecurityConfigurerAdapter() {

    private val bCryptPasswordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Throws
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
    }

    @Throws
    override fun configure(http: HttpSecurity) {
        val snappyAuthenticationFilter =
                SnappyAuthenticationFilter(
                        authenticationManagerBean(),
                        tokenUtils
                )
        snappyAuthenticationFilter.setFilterProcessesUrl("/api/login")
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(STATELESS)

        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh").permitAll()
        http.authorizeRequests().antMatchers(POST, "/api/user/save/**").permitAll()
        http.authorizeRequests().antMatchers(PUT, "/api/user/update/**").permitAll()
        http.authorizeRequests().antMatchers(DELETE,"/api/user/delete").permitAll()

        http.authorizeRequests().antMatchers(GET,
                "/api/users/**","/api/business/profiles/**")
                .hasAnyAuthority(
                        SnappyConst.SNP_ROOT,
                        SnappyConst.SNP_ADMIN,
                        SnappyConst.SNP_MANAGER
                )

        http.authorizeRequests().antMatchers(POST,
                "/api/profile/addAdminUser/**")
                .hasAnyAuthority(SnappyConst.SNP_ROOT)

        http.authorizeRequests().antMatchers(POST,
                "/api/profile/addManagerUser/**")
                .hasAnyAuthority(
                        SnappyConst.SNP_ROOT,
                        SnappyConst.SNP_ADMIN
                )

        http.authorizeRequests().antMatchers(POST,
                "/api/profile/addUser/**")
                .hasAnyAuthority(
                        SnappyConst.SNP_ROOT,
                        SnappyConst.SNP_ADMIN,
                        SnappyConst.SNP_MANAGER
                )

        http.authorizeRequests().anyRequest().authenticated()
        http.addFilter(snappyAuthenticationFilter)
        http.addFilterBefore(SnappyAuthorizationFilter(tokenUtils),
                UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager =
            super.authenticationManagerBean()
}