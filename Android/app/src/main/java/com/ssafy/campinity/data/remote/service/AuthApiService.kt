package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.auth.AuthRequest
import com.ssafy.campinity.data.remote.datasource.auth.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface AuthApiService {
    @GET("/api/v4/members/login-kakao")
    suspend fun loginRequest(@Body body: AuthRequest): AuthResponse
}