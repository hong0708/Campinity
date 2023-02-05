package com.ssafy.campinity.data.remote.datasource.search

import com.ssafy.campinity.data.remote.service.SearchApiService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchRemoteDataSource {

    override suspend fun getCampsitesByFiltering(filter: SearchFilterRequest): List<SearchBriefResponse> {
        return searchApiService.getCampsitesByFiltering(
            filter.allowAnimal,
            filter.amenity,
            filter.doName,
            filter.fclty,
            filter.industry,
            filter.keyword,
            filter.openSeason,
            filter.sigunguName,
            filter.theme
        )
    }
}