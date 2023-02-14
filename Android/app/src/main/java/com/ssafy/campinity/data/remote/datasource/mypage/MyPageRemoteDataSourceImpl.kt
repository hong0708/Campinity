package com.ssafy.campinity.data.remote.datasource.mypage

import com.ssafy.campinity.data.remote.datasource.user.UserResponse
import com.ssafy.campinity.data.remote.service.MyPageApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class MyPageRemoteDataSourceImpl @Inject constructor(
    private val myPageApiService: MyPageApiService
) : MyPageRemoteDataSource {

    override suspend fun getNotes(): MyPageNoteResponse =
        myPageApiService.getNotes()

    override suspend fun getUserInfo(): MyPageUserResponse =
        myPageApiService.getUserInfo()

    override suspend fun requestLogout(body: LogoutRequest): Boolean =
        myPageApiService.requestLogout(body)

    override suspend fun editUserInfo(
        nickName: String,
        isChanged: Boolean,
        profileImg: MultipartBody.Part?
    ): UserResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["nickName"] = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        map["isChanged"] = isChanged.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        return myPageApiService.editUserInfo(map, profileImg)
    }

    override suspend fun getScrapCampsites(): List<ScrapCampsiteResponse> =
        myPageApiService.getScrapCampsites()
}