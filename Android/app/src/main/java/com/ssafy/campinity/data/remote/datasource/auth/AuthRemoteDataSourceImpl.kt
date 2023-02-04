package com.ssafy.campinity.data.remote.datasource.auth

import com.ssafy.campinity.data.remote.service.AuthApiService
import com.ssafy.campinity.data.remote.service.RefreshApiService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val refreshApiService: RefreshApiService
) : AuthRemoteDataSource {

    override suspend fun loginRequest(code: String): AuthResponse =
        authApiService.loginRequest(code)

    override suspend fun getNewToken(): AuthResponse =
        refreshApiService.getNewToken()
}