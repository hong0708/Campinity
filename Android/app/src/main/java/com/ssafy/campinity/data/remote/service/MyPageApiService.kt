package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.mypage.LogoutRequest
import com.ssafy.campinity.data.remote.datasource.mypage.MyPageNoteResponse
import com.ssafy.campinity.data.remote.datasource.mypage.MyPageUserResponse
import com.ssafy.campinity.data.remote.datasource.mypage.ScrapCampsiteResponse
import com.ssafy.campinity.data.remote.datasource.user.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface MyPageApiService {

    @GET("/api/v2/messages/my-messages")
    suspend fun getNotes(): MyPageNoteResponse

    @GET("/api/v4/members/profiles")
    suspend fun getUserInfo(): MyPageUserResponse

    @POST("/api/v4/members/logout")
    suspend fun requestLogout(@Body body: LogoutRequest): Boolean

    @Multipart
    @POST("/api/v4/members/edit-member-info")
    suspend fun editUserInfo(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part?
    ): UserResponse

    @GET("/api/v1/campsites/scrap-lists")
    suspend fun getScrapCampsites(): List<ScrapCampsiteResponse>
}