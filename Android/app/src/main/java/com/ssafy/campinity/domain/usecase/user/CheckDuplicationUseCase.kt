package com.ssafy.campinity.domain.usecase.user

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckDuplicationUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend fun checkDuplication(nickname: String): Resource<Boolean> = withContext(Dispatchers.IO) {
        userRepository.checkDuplication(nickname)
    }
}