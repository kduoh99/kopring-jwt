package com.study.kopringjwt.user.api

import com.study.kopringjwt.global.auth.jwt.JwtInfo
import com.study.kopringjwt.global.common.BaseResponse
import com.study.kopringjwt.user.api.dto.request.LoginRequest
import com.study.kopringjwt.user.api.dto.request.RefreshRequest
import com.study.kopringjwt.user.api.dto.request.RegisterUserRequest
import com.study.kopringjwt.user.application.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterUserRequest): BaseResponse<Unit> =
        userService.registerUser(request)

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): BaseResponse<JwtInfo> =
        userService.loginUser(request)

    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody request: RefreshRequest): BaseResponse<String> =
        userService.refreshToken(request)
}
