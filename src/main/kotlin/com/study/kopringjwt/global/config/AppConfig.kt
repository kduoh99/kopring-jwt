package com.study.kopringjwt.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class AppConfig {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper()
}
