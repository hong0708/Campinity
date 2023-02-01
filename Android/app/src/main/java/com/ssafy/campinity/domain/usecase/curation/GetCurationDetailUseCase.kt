package com.ssafy.campinity.domain.usecase.curation

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.curation.CurationDetailItem
import com.ssafy.campinity.domain.repository.CurationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurationDetailUseCase @Inject constructor(
    private val curationRepository: CurationRepository
) {
    suspend operator fun invoke(curationId: String): Resource<CurationDetailItem> =
        withContext(Dispatchers.IO) {
            curationRepository.getCuration(curationId)
        }
}