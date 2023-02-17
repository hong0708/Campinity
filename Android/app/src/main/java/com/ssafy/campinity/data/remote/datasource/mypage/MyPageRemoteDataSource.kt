package com.ssafy.campinity.data.remote.datasource.mypage

import com.ssafy.campinity.data.remote.datasource.user.UserResponse
import okhttp3.MultipartBody

interface MyPageRemoteDataSource {

    suspend fun getNotes(): MyPageNoteResponse

    suspend fun getUserInfo(): MyPageUserResponse

    suspend fun requestLogout(body: LogoutRequest): Boolean

    suspend fun editUserInfo(
        nickName: String,
        isChanged: Boolean,
        profileImg: MultipartBody.Part?
    ): UserResponse

    suspend fun getScrapCampsites(): List<ScrapCampsiteResponse>
}