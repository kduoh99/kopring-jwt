package com.study.kopringjwt.global.exception

class CustomException(
    val customErrorCode: CustomErrorCode
) : RuntimeException()
