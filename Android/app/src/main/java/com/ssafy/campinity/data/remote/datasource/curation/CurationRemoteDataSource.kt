package com.ssafy.campinity.data.remote.datasource.curation

interface CurationRemoteDataSource {

    suspend fun getCurations(curationCategory: String): List<CurationResponse>

    suspend fun getCuration(curationId: String): CurationDetailResponse

    suspend fun scrapCuration(curationId: String): CurationScrapResponse

    suspend fun getScrapCurations(): List<CurationResponse>
}