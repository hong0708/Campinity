package com.ssafy.campinity.domain.usecase.mypage

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.user.User
import com.ssafy.campinity.domain.repository.MyPageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditUserInfoUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(
        nickName: String,
        isChanged: Boolean,
        profileImg: MultipartBody.Part?
    ): Resource<User> = withContext(Dispatchers.IO) {
        myPageRepository.editUserInfo(nickName, isChanged, profileImg)
    }
}