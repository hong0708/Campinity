package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.user.UserRemoteDataSource
import com.ssafy.campinity.data.remote.datasource.user.UserRequest
import com.ssafy.campinity.domain.entity.user.User
import com.ssafy.campinity.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun editUserInfo(body: UserRequest): Resource<User> =
        wrapToResource(Dispatchers.IO) { userRemoteDataSource.editUserInfo(body)!!.toDomainModel() }
}