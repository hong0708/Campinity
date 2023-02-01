package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.curation.CurationDetailResponse
import com.ssafy.campinity.data.remote.datasource.curation.CurationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurationApiService {

    @GET("/api/v7/curations/lists")
    suspend fun getCurations(
        @Query("curationCategory") curationCategory: String
    ): List<CurationResponse>

    @GET("/api/v7/curations/{curationId}")
    suspend fun getCuration(@Path("curationId") curationId: String): CurationDetailResponse
}