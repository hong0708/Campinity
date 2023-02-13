package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.fcm.FCMMessageRequest
import com.ssafy.campinity.data.remote.datasource.fcm.FCMRemoteDataSource
import com.ssafy.campinity.data.remote.datasource.fcm.FCMTokenRequest
import com.ssafy.campinity.domain.entity.fcm.FCMToken
import com.ssafy.campinity.domain.repository.FCMRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FCMRepositoryImpl @Inject constructor(
    private val fcmRemoteDataSource: FCMRemoteDataSource
) : FCMRepository {

    override suspend fun renewToken(fcmToken: String): Resource<FCMToken> =
        wrapToResource(Dispatchers.IO) {
            fcmRemoteDataSource.renewToken(fcmToken).toDomainModel()
        }

    override suspend fun subscribeCampsite(body: FCMTokenRequest): Resource<FCMToken> =
        wrapToResource(Dispatchers.IO) {
            fcmRemoteDataSource.subscribeCampsite(body).toDomainModel()
        }

    override suspend fun createHelpNote(body: FCMMessageRequest): Resource<Int> =
        wrapToResource(Dispatchers.IO) {
            fcmRemoteDataSource.createHelpNote(body)
        }
}