package com.ssafy.campinity.data.remote.datasource.user

import okhttp3.MultipartBody

interface UserRemoteDataSource {

    suspend fun editUserInfo(nickName: String, profileImg: MultipartBody.Part?): UserResponse

}