package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.data.remote.datasource.search.SearchReviewRequest
import com.ssafy.campinity.domain.repository.SearchRepository
import javax.inject.Inject

class WriteReviewUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(review: SearchReviewRequest) = searchRepository.writeReview(review)
}