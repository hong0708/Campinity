package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.domain.repository.SearchRepository
import javax.inject.Inject

class ScrapCampsiteUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(campsiteId: String) = searchRepository.scrapCampsite(campsiteId)
}