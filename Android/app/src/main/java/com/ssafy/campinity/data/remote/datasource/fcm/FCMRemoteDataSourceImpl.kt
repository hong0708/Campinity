package com.ssafy.campinity.data.remote.datasource.fcm

import com.ssafy.campinity.data.remote.service.FCMApiService
import javax.inject.Inject

class FCMRemoteDataSourceImpl @Inject constructor(
    private val fcmApiService: FCMApiService
) : FCMRemoteDataSource {

    override suspend fun renewToken(fcmToken: String): FCMTokenResponse =
        fcmApiService.renewToken(fcmToken)
}