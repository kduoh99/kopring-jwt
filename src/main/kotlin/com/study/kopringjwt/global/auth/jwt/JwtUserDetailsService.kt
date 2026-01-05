package com.study.kopringjwt.global.auth.jwt

import com.study.kopringjwt.global.exception.CustomException
import com.study.kopringjwt.user.domain.UserMapper
import com.study.kopringjwt.user.domain.repository.UserRepository
import com.study.kopringjwt.user.exception.UserErrorCode
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw CustomException(UserErrorCode.USER_NOT_FOUND)
        return JwtUserDetails(userMapper.toDomain(user))
    }
}
