package com.ssafy.campinity.data.remote.datasource.auth

interface AuthRemoteDataSource {
    suspend fun loginRequest(body: AuthRequest): AuthResponse
}