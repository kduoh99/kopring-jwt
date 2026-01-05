package com.study.kopringjwt.global.auth.exception

import com.study.kopringjwt.global.exception.CustomException
import com.study.kopringjwt.global.exception.ErrorResponseSender
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter(
    private val errorResponseSender: ErrorResponseSender
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            errorResponseSender.send(response, e.customErrorCode)
        }
    }
}
