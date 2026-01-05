package com.study.kopringjwt.global.auth.jwt

import com.study.kopringjwt.global.auth.exception.JwtErrorCode
import com.study.kopringjwt.global.exception.CustomException
import com.study.kopringjwt.user.domain.User
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.SignatureException
import java.util.Date
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils(
    private val jwtProperties: JwtProperties,
    private val userDetailsService: UserDetailsService,
) {
    companion object {
        const val TOKEN_TYPE_ACCESS = "access"
        const val TOKEN_TYPE_REFRESH = "refresh"
    }

    private val secretKey: SecretKey = SecretKeySpec(
        jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().algorithm
    )

    fun generate(user: User): JwtInfo {
        val accessToken = createToken(user, jwtProperties.accessExpired, TOKEN_TYPE_ACCESS)
        val refreshToken = createToken(user, jwtProperties.refreshExpired, TOKEN_TYPE_REFRESH)
        return JwtInfo("Bearer $accessToken", "Bearer $refreshToken")
    }

    private fun createToken(user: User, tokenExpired: Long, tokenType: String): String {
        val now = Date().time
        return Jwts.builder()
            .claim("id", user.id)
            .claim("email", user.email)
            .claim("role", user.role)
            .claim("type", tokenType)
            .issuedAt(Date(now))
            .expiration(Date(now + tokenExpired))
            .signWith(secretKey)
            .compact()
    }

    fun checkTokenInfo(token: String) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        } catch (e: ExpiredJwtException) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_EXPIRED)
        } catch (e: SignatureException) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_SIGNATURE_ERROR)
        } catch (e: MalformedJwtException) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_ERROR)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_UNSUPPORTED_ERROR)
        } catch (e: Exception) {
            throw CustomException(JwtErrorCode.JWT_UNKNOWN_EXCEPTION)
        }
    }

    fun getToken(token: String): String = token.removePrefix("Bearer ")

    fun getUsername(token: String): String =
        Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(token).payload.get("email", String::class.java)

    fun isRefreshToken(token: String): Boolean {
        val type = Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(token).payload.get("type", String::class.java)
        return type == TOKEN_TYPE_REFRESH
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(getToken(token)))
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    fun refreshAccessToken(user: User): String =
        "Bearer " + createToken(user, jwtProperties.accessExpired, TOKEN_TYPE_ACCESS)
}
