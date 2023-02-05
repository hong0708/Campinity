package com.ssafy.campinity.domain.usecase.user

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.user.User
import com.ssafy.campinity.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun editUserInfo(
        nickname: String,
        profileImg: MultipartBody.Part?,
        fcmToken: String
    ): Resource<User> = withContext(Dispatchers.IO) {
        userRepository.editUserInfo(nickname, profileImg, fcmToken)
    }

    suspend fun editUserInfoWithoutimg(
        nickname: String,
        fcmToken: String
    ): Resource<User> = withContext(Dispatchers.IO) {
        userRepository.editUserInfoWithoutImg(nickname, fcmToken)
    }
}