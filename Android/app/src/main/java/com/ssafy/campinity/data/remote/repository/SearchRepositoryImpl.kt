package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.search.SearchRemoteDataSource
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun getCampsitesByArea(
        sido: String, gugun: String
    ): Resource<List<CampsiteBriefInfo>> = wrapToResource(Dispatchers.IO) {
        searchRemoteDataSource.getCampsitesByArea(sido, gugun).map { it.toDomainModel() }
    }
}