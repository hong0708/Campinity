package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterClusteringRequest
import com.ssafy.campinity.domain.entity.search.ClusteringDo
import com.ssafy.campinity.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class getCampsitesByDoUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(filter: SearchFilterClusteringRequest): Resource<List<ClusteringDo>> =
        withContext(Dispatchers.IO) {
            searchRepository.getCampsitesByDo(filter)
        }
}