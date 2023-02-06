package com.ssafy.campinity.data.remote.datasource.search

interface SearchRemoteDataSource {

    suspend fun getCampsitesByFiltering(filter: SearchFilterRequest): List<SearchBriefResponse>

    suspend fun getCampsitesByScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ): List<SearchBriefResponse>

    suspend fun getCampsiteDetail(campsiteId: String): SearchDetailResponse
}