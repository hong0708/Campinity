package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampsitesByAreaUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(sido: String, gugun: String): Resource<List<CampsiteBriefInfo>> =
        withContext(Dispatchers.IO) {
            searchRepository.getCampsitesByArea(sido, gugun)
        }
}