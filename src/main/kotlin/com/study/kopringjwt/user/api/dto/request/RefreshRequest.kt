package com.study.kopringjwt.user.api.dto.request

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank(message = "리프레시 토큰을 입력해 주세요.")
    val refreshToken: String = ""
)
