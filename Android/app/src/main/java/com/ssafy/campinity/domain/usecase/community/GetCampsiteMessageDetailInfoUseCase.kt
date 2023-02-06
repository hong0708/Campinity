package com.ssafy.campinity.domain.usecase.community

import com.ssafy.campinity.domain.repository.CommunityRepository
import javax.inject.Inject

class GetCampsiteMessageDetailInfoUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(messageId: String) =
        communityRepository.getCampsiteMessageDetail(messageId)
}