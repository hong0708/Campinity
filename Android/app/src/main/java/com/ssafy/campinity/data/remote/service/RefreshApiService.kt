package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.auth.AuthResponse
import retrofit2.http.GET

interface RefreshApiService {

    @GET("/api/v4/members/reissue")
    suspend fun getNewToken(): AuthResponse
}