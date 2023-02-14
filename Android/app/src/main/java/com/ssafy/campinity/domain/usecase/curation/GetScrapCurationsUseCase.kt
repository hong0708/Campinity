package com.ssafy.campinity.domain.usecase.curation

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.curation.CurationItem
import com.ssafy.campinity.domain.repository.CurationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetScrapCurationsUseCase @Inject constructor(
    private val curationRepository: CurationRepository
) {
    suspend operator fun invoke(): Resource<List<CurationItem>> =
        withContext(Dispatchers.IO) {
            curationRepository.getScrapCurations()
        }
}