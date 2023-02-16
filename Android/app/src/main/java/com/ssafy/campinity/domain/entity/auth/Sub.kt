package com.ssafy.campinity.domain.entity.auth

data class Sub(
    val uuid: String,
    val email: String,
    val nickname: String,
    val type: String
)