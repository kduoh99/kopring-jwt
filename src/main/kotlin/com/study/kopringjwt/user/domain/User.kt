package com.study.kopringjwt.user.domain

data class User(
    val id: Long? = null,
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val role: UserRoles = UserRoles.ROLE_USER
)
