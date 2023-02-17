package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.curation.CurationDetailResponse
import com.ssafy.campinity.data.remote.datasource.curation.CurationResponse
import com.ssafy.campinity.data.remote.datasource.curation.CurationScrapResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CurationApiService {

    @GET("/api/v7/curations/lists")
    suspend fun getCurations(
        @Query("curationCategory") curationCategory: String
    ): List<CurationResponse>

    @GET("/api/v7/curations/{curationId}")
    suspend fun getCuration(@Path("curationId") curationId: String): CurationDetailResponse

    @PUT("/api/v7/curations/scraps/{curationId}")
    suspend fun scrapCuration(
        @Path("curationId") curationId: String
    ): CurationScrapResponse

    @GET("/api/v7/curations/scrap-lists")
    suspend fun getScrapCurations(): List<CurationResponse>
}