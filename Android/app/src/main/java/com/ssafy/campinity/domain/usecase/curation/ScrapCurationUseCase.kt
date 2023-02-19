package com.ssafy.campinity.domain.usecase.curation

import com.ssafy.campinity.domain.repository.CurationRepository
import javax.inject.Inject

class ScrapCurationUseCase @Inject constructor(
    private val curationRepository: CurationRepository
) {
    suspend operator fun invoke(curationId: String) = curationRepository.scrapCuration(curationId)
}