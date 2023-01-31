package com.ssafy.campinity.data.remote.datasource.search

import com.ssafy.campinity.data.remote.service.SearchApiService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchRemoteDataSource {

    override suspend fun getCampsitesByArea(): List<SearchAreaResponse> =
        searchApiService.getCampsitesByArea()
}