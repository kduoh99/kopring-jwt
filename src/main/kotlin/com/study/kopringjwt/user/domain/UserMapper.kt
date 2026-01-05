package com.study.kopringjwt.user.domain

import com.study.kopringjwt.global.common.Mapper
import com.study.kopringjwt.user.api.dto.request.RegisterUserRequest
import org.springframework.stereotype.Component

@Component
class UserMapper : Mapper<User, UserEntity> {

    override fun toDomain(entity: UserEntity): User =
        User(
            id = entity.id,
            email = entity.email,
            name = entity.name,
            password = entity.password,
            role = entity.role
        )

    override fun toEntity(domain: User): UserEntity =
        UserEntity(
            email = domain.email,
            name = domain.name,
            password = domain.password,
            role = domain.role
        )

    fun toDomain(request: RegisterUserRequest, encodedPassword: String): User =
        User(
            email = request.email,
            name = request.name,
            password = encodedPassword
        )
}
