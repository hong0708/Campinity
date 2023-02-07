package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.mypage.LogoutRequest
import com.ssafy.campinity.data.remote.datasource.mypage.MyPageNoteResponse
import com.ssafy.campinity.data.remote.datasource.mypage.MyPageUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyPageApiService {

    @GET("/api/v2/messages/my-messages")
    suspend fun getNotes(): MyPageNoteResponse

    @GET("/api/v4/members/profiles")
    suspend fun getUserInfo(): MyPageUserResponse

    @POST("/api/v4/members/logout")
    suspend fun requestLogout(@Body body: LogoutRequest): Boolean
}