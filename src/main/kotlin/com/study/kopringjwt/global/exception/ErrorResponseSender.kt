package com.study.kopringjwt.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.kopringjwt.global.common.BaseResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class ErrorResponseSender(
    private val objectMapper: ObjectMapper
) {
    fun send(response: HttpServletResponse, errorCode: CustomErrorCode) {
        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
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
