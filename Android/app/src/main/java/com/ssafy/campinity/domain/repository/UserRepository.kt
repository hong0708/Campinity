package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.user.UserRequest
import com.ssafy.campinity.domain.entity.user.User

interface UserRepository {

    suspend fun editUserInfo(body: UserRequest): Resource<User>
}