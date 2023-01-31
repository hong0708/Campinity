package com.ssafy.campinity.data.remote.datasource.search

interface SearchRemoteDataSource {

    suspend fun getCampsitesByArea(): List<SearchAreaResponse>
}