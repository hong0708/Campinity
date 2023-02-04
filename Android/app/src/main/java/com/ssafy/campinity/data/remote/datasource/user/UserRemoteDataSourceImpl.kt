package com.ssafy.campinity.data.remote.datasource.user

import com.ssafy.campinity.data.remote.service.UserApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRemoteDataSource {

    override suspend fun editUserInfo(
        nickName: String,
        profileImg: MultipartBody.Part?,
        fcmToken: String
    ): UserResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["nickName"] = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        map["fcmToken"] = fcmToken.toRequestBody("text/plain".toMediaTypeOrNull())
        return userApiService.editUserInfo(map, profileImg)
    }

    override suspend fun editUserInfoWithoutImg(nickName: String, fcmToken: String): UserResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["nickName"] = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        map["fcmToken"] = fcmToken.toRequestBody("text/plain".toMediaTypeOrNull())
        return userApiService.editUserInfo(map, null)
    }

    override suspend fun checkDuplication(nickName: String): Boolean {
        return userApiService.checkDuplication(nickName)
    }
}