package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.fcm.FCMMessageRequest
import com.ssafy.campinity.data.remote.datasource.fcm.FCMTokenRequest
import com.ssafy.campinity.domain.entity.fcm.FCMToken

interface FCMRepository {

    suspend fun renewToken(fcmToken: String): Resource<FCMToken>

    suspend fun subscribeCampsite(body: FCMTokenRequest): Resource<FCMToken>

    suspend fun createHelpNote(body: FCMMessageRequest): Resource<Int>
}