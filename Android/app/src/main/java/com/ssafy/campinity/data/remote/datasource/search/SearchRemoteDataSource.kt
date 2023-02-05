package com.ssafy.campinity.data.remote.datasource.search

interface SearchRemoteDataSource {

    suspend fun getCampsitesByFiltering(filter: SearchFilterRequest): List<SearchBriefResponse>
}