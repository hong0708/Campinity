package com.ssafy.campinity.domain.usecase.auth

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.auth.AuthRequest
import com.ssafy.campinity.domain.entity.auth.Token
import com.ssafy.campinity.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(body: AuthRequest): Resource<Token> = withContext(Dispatchers.IO) {
        authRepository.loginRequest(body)
    }
}