package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.user.User
import com.ssafy.campinity.domain.entity.user.UserProfile
import okhttp3.MultipartBody

interface UserRepository {

    suspend fun createUserInfo(
        nickName: String,
        profileImg: MultipartBody.Part?,
        fcmToken: String
    ): Resource<User>

    suspend fun createUserInfoWithoutImg(nickName: String, fcmToken: String): Resource<User>

    suspend fun checkDuplication(nickName: String): Resource<Boolean>

    suspend fun cancelSignUp(): Resource<Boolean>

    suspend fun getUserProfile(): Resource<UserProfile>
}