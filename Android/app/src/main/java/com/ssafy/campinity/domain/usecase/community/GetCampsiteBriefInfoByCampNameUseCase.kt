package com.ssafy.campinity.domain.usecase.community

import com.ssafy.campinity.domain.repository.CommunityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampsiteBriefInfoByCampNameUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(campName: String) =
        communityRepository.getCampsiteBriefInfoByCampName(campName)
}
