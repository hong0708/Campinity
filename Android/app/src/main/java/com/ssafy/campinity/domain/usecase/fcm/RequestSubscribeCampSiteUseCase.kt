package com.ssafy.campinity.domain.usecase.fcm

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.fcm.FCMTokenRequest
import com.ssafy.campinity.domain.entity.fcm.FCMToken
import com.ssafy.campinity.domain.repository.FCMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestSubscribeCampSiteUseCase @Inject constructor(
    private val fcmRepository: FCMRepository
) {
    suspend operator fun invoke(campsiteId: String, fcmToken: String): Resource<FCMToken> =
        withContext(Dispatchers.IO) {
            fcmRepository.subscribeCampsite(FCMTokenRequest(campsiteId, fcmToken))
        }
}