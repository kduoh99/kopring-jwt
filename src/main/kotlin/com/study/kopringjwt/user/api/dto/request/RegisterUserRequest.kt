package com.study.kopringjwt.user.api.dto.request

data class RegisterUserRequest(
    val email: String = "",
    val name: String = "",
    val password: String = ""
)
