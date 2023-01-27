package com.ssafy.campinity.data.remote.datasource.user

interface UserRemoteDataSource {

    suspend fun editUserInfo(body: UserRequest): UserResponse?

}