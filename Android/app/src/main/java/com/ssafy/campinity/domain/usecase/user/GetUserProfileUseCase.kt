package com.ssafy.campinity.domain.usecase.user

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.user.UserProfile
import com.ssafy.campinity.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Resource<UserProfile> =
        withContext(Dispatchers.IO) {
            userRepository.getUserProfile()
        }
}