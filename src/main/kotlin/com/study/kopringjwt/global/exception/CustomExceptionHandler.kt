package com.study.kopringjwt.global.exception

import com.study.kopringjwt.global.common.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<BaseResponse<Unit>> {
        val response = BaseResponse<Unit>(
            status = e.customErrorCode.status.value(),
            state = e.customErrorCode.state,
            message = e.customErrorCode.message
        )
        return ResponseEntity(response, e.customErrorCode.status)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Unit>> {
        val message = e.bindingResult.fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }

        val response = BaseResponse<Unit>(
            status = HttpStatus.BAD_REQUEST.value(),
            state = "BAD_REQUEST",
            message = message
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }
}
