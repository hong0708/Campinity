package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.auth.AuthResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NoteApiService {
    @GET("/api/v6/questions/lists/{campsiteId}")
    suspend fun noteQuestionRequest(@Query("campsiteId") uuid: String): AuthResponse
}