package com.study.kopringjwt

import com.study.kopringjwt.global.common.GetAuthenticatedId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @PostMapping
    fun test(
        @GetAuthenticatedId id: Long
    ): String {
        return "id: $id"
    }
}
