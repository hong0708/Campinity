package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.auth.AuthRequest
import com.ssafy.campinity.domain.entity.auth.Token

interface AuthRepository {
    suspend fun loginRequest(body: AuthRequest): Resource<Token>
}