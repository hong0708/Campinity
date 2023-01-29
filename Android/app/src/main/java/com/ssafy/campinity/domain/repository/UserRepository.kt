package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.user.User
import okhttp3.MultipartBody

interface UserRepository {

    suspend fun editUserInfo(nickName: String, profileImg: MultipartBody.Part?): Resource<User>
}