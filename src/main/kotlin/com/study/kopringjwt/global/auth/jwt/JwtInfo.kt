package com.study.kopringjwt.global.auth.jwt

data class JwtInfo(
    val accessToken: String,
    val refreshToken: String
)
