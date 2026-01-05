package com.study.kopringjwt.global.auth.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.kopringjwt.global.common.BaseResponse
import com.study.kopringjwt.global.exception.CustomErrorCode
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class ErrorResponseSender(
    private val objectMapper: ObjectMapper
) {
    fun send(response: HttpServletResponse, errorCode: CustomErrorCode) {
        response.status = errorCode.status.value()
        response.writer.write(
            objectMapper.writeValueAsString(
                BaseResponse<Unit>(
                    status = errorCode.status.value(),
                    state = errorCode.state,
                    message = errorCode.message
                )
            )
        )
    }
}
