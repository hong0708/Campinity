package com.ssafy.campinity.domain.usecase.community

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.communitycampsite.CommunityCampsiteMessageRequest
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo
import com.ssafy.campinity.domain.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateCampsiteMessageUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        communityCampsiteMessageRequest: CommunityCampsiteMessageRequest
    ): Resource<CampsiteMessageDetailInfo> =
        withContext(Dispatchers.IO) {
            communityRepository.createCampsiteMessage(communityCampsiteMessageRequest)
        }
}
