package com.study.kopringjwt.user.domain.repository

import com.study.kopringjwt.user.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
}
