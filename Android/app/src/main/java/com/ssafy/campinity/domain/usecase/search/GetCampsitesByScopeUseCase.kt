package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.domain.repository.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampsitesByScopeUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ) = searchRepository.getCampsitesByScope(
        bottomRightLat,
        bottomRightLng,
        topLeftLat,
        topLeftLng
    )
}