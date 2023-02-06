package com.ssafy.campinity.domain.usecase.community

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo
import com.ssafy.campinity.domain.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampsiteMessageDetailInfoUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(messageId: String): Resource<CampsiteMessageDetailInfo> =
        withContext(Dispatchers.IO) {
            communityRepository.getCampsiteMessageDetailInfo(messageId)
        }
}