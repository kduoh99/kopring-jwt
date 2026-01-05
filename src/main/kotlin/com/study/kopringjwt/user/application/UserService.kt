package com.study.kopringjwt.user.application

import com.study.kopringjwt.global.auth.exception.JwtErrorCode
import com.study.kopringjwt.global.auth.jwt.JwtInfo
import com.study.kopringjwt.global.auth.jwt.JwtUtils
import com.study.kopringjwt.global.common.BaseResponse
import com.study.kopringjwt.global.exception.CustomException
import com.study.kopringjwt.user.api.dto.request.LoginRequest
import com.study.kopringjwt.user.api.dto.request.RefreshRequest
import com.study.kopringjwt.user.api.dto.request.RegisterUserRequest
import com.study.kopringjwt.user.domain.UserMapper
import com.study.kopringjwt.user.domain.repository.UserRepository
import com.study.kopringjwt.user.exception.UserErrorCode
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtils: JwtUtils
) {

    @Transactional
    fun registerUser(request: RegisterUserRequest): BaseResponse<Unit> {
        if (userRepository.existsByEmail(request.email)) {
            throw CustomException(UserErrorCode.USER_ALREADY_EXIST)
        }
        val user = userMapper.toDomain(request, passwordEncoder.encode(request.password))
        userRepository.save(userMapper.toEntity(user))
        return BaseResponse(message = "회원가입 성공")
    }

    fun loginUser(request: LoginRequest): BaseResponse<JwtInfo> {
        val userEntity = userRepository.findByEmail(request.email)
            ?: throw CustomException(UserErrorCode.USER_NOT_FOUND)

        if (!passwordEncoder.matches(request.password, userEntity.password)) {
            throw CustomException(UserErrorCode.USER_NOT_MATCH)
        }

        return BaseResponse(
            message = "로그인 성공",
            data = jwtUtils.generate(userMapper.toDomain(userEntity))
        )
    }

    fun refreshToken(request: RefreshRequest): BaseResponse<String> {
        val token = jwtUtils.getToken(request.refreshToken)
        jwtUtils.checkTokenInfo(token)

        if (!jwtUtils.isRefreshToken(token)) {
            throw CustomException(JwtErrorCode.JWT_INVALID_TOKEN_TYPE)
        }

        val userEntity = userRepository.findByEmail(jwtUtils.getUsername(token))
            ?: throw CustomException(UserErrorCode.USER_NOT_FOUND)

        return BaseResponse(
            message = "토큰 갱신 성공",
            data = jwtUtils.refreshAccessToken(userMapper.toDomain(userEntity))
        )
    }
}
