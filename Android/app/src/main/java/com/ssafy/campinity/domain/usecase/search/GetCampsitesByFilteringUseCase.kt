package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterRequest
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfoPaging
import com.ssafy.campinity.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampsitesByFilteringUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(filter: SearchFilterRequest): Resource<CampsiteBriefInfoPaging> =
        withContext(Dispatchers.IO) {
            searchRepository.getCampsitesByFiltering(filter)
        }
}