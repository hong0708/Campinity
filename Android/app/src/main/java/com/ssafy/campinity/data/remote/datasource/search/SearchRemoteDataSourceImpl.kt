package com.ssafy.campinity.data.remote.datasource.search

import com.ssafy.campinity.data.remote.service.SearchApiService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchRemoteDataSource {

    override suspend fun getCampsitesByArea(sido: String, gugun: String): List<SearchAreaResponse> =
        searchApiService.getCampsitesByArea(sido, gugun)
}