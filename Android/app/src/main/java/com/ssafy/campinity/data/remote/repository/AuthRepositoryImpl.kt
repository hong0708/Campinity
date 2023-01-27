package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.campinity.data.remote.datasource.auth.AuthRequest
import com.ssafy.campinity.domain.entity.auth.Token
import com.ssafy.campinity.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun loginRequest(body: AuthRequest): Resource<Token> =
        wrapToResource(Dispatchers.IO) { authRemoteDataSource.loginRequest(body.accessToken).toDomainModel() }
}