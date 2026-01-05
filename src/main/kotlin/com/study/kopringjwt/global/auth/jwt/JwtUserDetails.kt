package com.study.kopringjwt.global.auth.jwt

import com.study.kopringjwt.user.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails(
    val user: User
) : UserDetails {
    val id: Long? = user.id

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(user.role.name))

    override fun getPassword(): String = user.password
    override fun getUsername(): String = user.email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
