package com.ssafy.campinity.data.remote.datasource.search

interface SearchRemoteDataSource {

    suspend fun getCampsitesByArea(sido: String, gugun: String): List<SearchAreaResponse>
}