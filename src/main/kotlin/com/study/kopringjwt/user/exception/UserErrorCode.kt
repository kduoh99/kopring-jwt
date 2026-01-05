package com.study.kopringjwt.user.exception

import com.study.kopringjwt.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class UserErrorCode(
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "존재하지 않는 유저입니다."),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "CONFLICT", "이미 존재하는 이메일입니다."),
    USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "이메일 또는 비밀번호가 잘못되었습니다.")
}
