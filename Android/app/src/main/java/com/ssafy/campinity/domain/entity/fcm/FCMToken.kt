package com.ssafy.campinity.domain.entity.fcm

data class FCMToken(
    val expiredDate: String,
    val subscribeCampId: String,
    val token: String
)