package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.user.UserRequest
import com.ssafy.campinity.data.remote.datasource.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("/api/v4/members/sign-up")
    suspend fun editUserInfo(
        @Body userRequest: UserRequest
    ) : UserResponse?
}