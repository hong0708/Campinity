package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.mypage.LogoutRequest
import com.ssafy.campinity.domain.entity.mypage.MyPageNote
import com.ssafy.campinity.domain.entity.mypage.MyPageUser
import com.ssafy.campinity.domain.entity.mypage.ScrapCampsite
import com.ssafy.campinity.domain.entity.user.User
import okhttp3.MultipartBody

interface MyPageRepository {

    suspend fun getNotes(): Resource<MyPageNote>

    suspend fun getUserInfo(): Resource<MyPageUser>

    suspend fun requestLogout(body: LogoutRequest): Resource<Boolean>

    suspend fun editUserInfo(
        nickName: String,
        isChanged: Boolean,
        profileImg: MultipartBody.Part?
    ): Resource<User>

    suspend fun getScrapCampsites(): Resource<List<ScrapCampsite>>
}