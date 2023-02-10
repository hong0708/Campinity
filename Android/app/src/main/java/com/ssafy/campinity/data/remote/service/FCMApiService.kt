package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.fcm.FCMTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FCMApiService {

    @POST("/api/v9/fcm/token")
    suspend fun renewToken(@Body fcmToken: String): FCMTokenResponse
}