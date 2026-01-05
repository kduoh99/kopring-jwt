package com.study.kopringjwt.global.auth.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtils,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = request.getHeader("Authorization")

        if (!token.isNullOrBlank()) {
            jwtUtils.checkTokenInfo(jwtUtils.getToken(token))
            SecurityContextHolder.getContext().authentication = jwtUtils.getAuthentication(token)
        }

        filterChain.doFilter(request, response)
    }
}
