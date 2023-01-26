package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.auth.AuthResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiService {
    @GET("/api/v4/members/login-kakao")
    suspend fun loginRequest(@Query("code") code: String): AuthResponse
}