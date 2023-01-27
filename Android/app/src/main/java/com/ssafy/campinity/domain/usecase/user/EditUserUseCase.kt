package com.ssafy.campinity.domain.usecase.user

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.user.UserRequest
import com.ssafy.campinity.domain.entity.user.User
import com.ssafy.campinity.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun editUserInfo(body: UserRequest): Resource<User> = withContext(Dispatchers.IO) {
        userRepository.editUserInfo(body)
    }
}