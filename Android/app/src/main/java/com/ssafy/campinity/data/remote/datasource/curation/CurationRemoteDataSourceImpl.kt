package com.ssafy.campinity.data.remote.datasource.curation

import com.ssafy.campinity.data.remote.service.CurationApiService
import javax.inject.Inject

class CurationRemoteDataSourceImpl @Inject constructor(
    private val curationApiService: CurationApiService
) : CurationRemoteDataSource {

    override suspend fun getCurations(curationCategory: String): List<CurationResponse> =
        curationApiService.getCurations(curationCategory)

    override suspend fun getCuration(curationId: String): CurationDetailResponse =
        curationApiService.getCuration(curationId)

    override suspend fun scrapCuration(curationId: String): CurationScrapResponse =
        curationApiService.scrapCuration(curationId)
}