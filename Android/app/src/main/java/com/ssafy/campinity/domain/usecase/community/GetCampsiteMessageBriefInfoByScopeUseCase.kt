package com.ssafy.campinity.domain.usecase.community

import com.ssafy.campinity.domain.repository.CommunityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampsiteMessageBriefInfoByScopeUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        bottomRightLat: Double,
        bottomRightLng: Double,
        campsiteId: String,
        topLeftLat: Double,
        topLeftLng: Double
    ) = communityRepository.getCampsiteMessageBriefInfoBYScope(
        bottomRightLat,
        bottomRightLng,
        campsiteId,
        topLeftLat,
        topLeftLng
    )
}
