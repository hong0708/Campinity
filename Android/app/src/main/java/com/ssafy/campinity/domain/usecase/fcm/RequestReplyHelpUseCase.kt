package com.ssafy.campinity.domain.usecase.fcm

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.fcm.FCMReplyRequest
import com.ssafy.campinity.domain.repository.FCMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestReplyHelpUseCase @Inject constructor(
    private val fcmRepository: FCMRepository
) {
    suspend operator fun invoke(body: FCMReplyRequest): Resource<Int?> =
        withContext(Dispatchers.IO) {
            fcmRepository.replyHelp(body)
        }
}