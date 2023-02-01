package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.search.SearchAreaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("/api/v1/campsites/conditions")
    suspend fun getCampsitesByArea(
        @Query("doName") sido: String,
        @Query("sigunguName") gugun: String
    ): List<SearchAreaResponse>
}