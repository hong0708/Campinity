package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.mypage.MyPageNoteResponse
import retrofit2.http.GET

interface MyPageApiService {

    @GET("/api/v2/messages/my-messages")
    suspend fun getNotes(): MyPageNoteResponse
}