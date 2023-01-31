package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.search.SearchAreaResponse
import retrofit2.http.GET

interface SearchApiService {

    @GET("/api/v1/campsite/conditions")
    suspend fun getCampsitesByArea(): List<SearchAreaResponse>
}