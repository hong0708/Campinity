package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.domain.repository.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampsiteReviewNotesUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(
        campsiteId: String,
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double,
    ) = searchRepository.getCampsiteReviewNotes(
        campsiteId,
        bottomRightLat,
        bottomRightLng,
        topLeftLat,
        topLeftLng,
    )
}