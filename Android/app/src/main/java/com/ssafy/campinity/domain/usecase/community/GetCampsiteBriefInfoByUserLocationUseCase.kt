package com.ssafy.campinity.domain.usecase.community

import com.ssafy.campinity.domain.repository.CommunityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampsiteBriefInfoByUserLocationUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ) = communityRepository.getCampsiteBriefInfoByUserLocation(
        bottomRightLat,
        bottomRightLng,
        topLeftLat,
        topLeftLng
    )
}
