package com.ssafy.campinity.data.remote.datasource.auth

import com.ssafy.campinity.data.remote.service.AuthApiService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthRemoteDataSource {
    override suspend fun loginRequest(code: String): AuthResponse {
        return authApiService.loginRequest(code)
    }
}