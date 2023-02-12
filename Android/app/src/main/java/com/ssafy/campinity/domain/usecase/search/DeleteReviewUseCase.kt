package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.domain.repository.SearchRepository
import javax.inject.Inject

class DeleteReviewUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(reviewId: String) = searchRepository.deleteReview(reviewId)
}