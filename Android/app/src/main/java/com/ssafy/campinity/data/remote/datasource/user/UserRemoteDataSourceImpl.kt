package com.ssafy.campinity.data.remote.datasource.user

import com.ssafy.campinity.data.remote.service.UserApiService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRemoteDataSource {
    override suspend fun editUserInfo(body: UserRequest): UserResponse? {
        return userApiService.editUserInfo(body)
    }
}