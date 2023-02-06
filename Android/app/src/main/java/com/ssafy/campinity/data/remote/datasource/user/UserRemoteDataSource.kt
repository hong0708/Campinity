package com.ssafy.campinity.data.remote.datasource.user

import okhttp3.MultipartBody

interface UserRemoteDataSource {

    suspend fun editUserInfo(nickName: String, profileImg: MultipartBody.Part?, fcmToken: String): UserResponse

    suspend fun editUserInfoWithoutImg(nickName: String, fcmToken: String): UserResponse

    suspend fun checkDuplication(nickName: String): Boolean

    suspend fun cancelSignUp(): Boolean
}