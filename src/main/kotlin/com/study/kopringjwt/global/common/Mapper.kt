package com.study.kopringjwt.global.common

interface Mapper<D, E> {
    fun toDomain(entity: E): D
    fun toEntity(domain: D): E
}
