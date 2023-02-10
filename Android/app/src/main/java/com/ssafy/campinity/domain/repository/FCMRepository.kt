package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.fcm.FCMToken

interface FCMRepository {

    suspend fun renewToken(fcmToken: String): Resource<FCMToken>
}