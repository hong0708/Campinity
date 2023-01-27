package com.ssafy.campinity.domain.entity.auth

data class Token(
    val accessToken: String?,
    val refreshToken: String
)