package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.mypage.MyPageNoteResponse
import com.ssafy.campinity.data.remote.datasource.mypage.MyPageUserResponse
import retrofit2.http.GET

interface MyPageApiService {

    @GET("/api/v2/messages/my-messages")
    suspend fun getNotes(): MyPageNoteResponse

    @GET("/api/v4/members/profiles")
    suspend fun getUserInfo(): MyPageUserResponse
}