package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.fcm.FCMMessageRequest
import com.ssafy.campinity.data.remote.datasource.fcm.FCMReplyRequest
import com.ssafy.campinity.data.remote.datasource.fcm.FCMTokenRequest
import com.ssafy.campinity.data.remote.datasource.fcm.FCMTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface FCMApiService {

    @POST("/api/v9/fcm/token")
    suspend fun renewToken(@Body fcmToken: String): FCMTokenResponse

    @PUT("/api/v9/fcm/subscribe")
    suspend fun subscribeCampsite(@Body body: FCMTokenRequest): FCMTokenResponse

    @POST("/api/v9/fcm/to-many")
    suspend fun createHelpNote(@Body body: FCMMessageRequest): Int

    @POST("/api/v9/fcm/reply")
    suspend fun replyHelp(@Body body: FCMReplyRequest): Int?
}