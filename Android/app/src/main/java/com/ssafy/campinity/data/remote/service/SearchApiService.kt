package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.search.SearchBriefResponse
import com.ssafy.campinity.data.remote.datasource.search.SearchDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApiService {

    @GET("/api/v1/campsites/conditions")
    suspend fun getCampsitesByFiltering(
        @Query("allowAnimal") allowAnimal: String?,
        @Query("amenity") amenity: String?,
        @Query("doName") doName: String?,
        @Query("fclty") fclty: String?,
        @Query("industry") industry: String?,
        @Query("keyword") keyword: String?,
        @Query("openSeason") openSeason: String?,
        @Query("sigunguName") sigunguName: String?,
        @Query("theme") theme: String?,
    ): List<SearchBriefResponse>

    @GET("/api/v1/campsites/scope")
    suspend fun getCampsitesByScope(
        @Query("bottomRightLat") bottomRightLat: Double,
        @Query("bottomRightLng") bottomRightLng: Double,
        @Query("topLeftLat") topLeftLat: Double,
        @Query("topLeftLng") topLeftLng: Double
    ): List<SearchBriefResponse>

    @GET("/api/v1/campsites/detail/{campsiteId}")
    suspend fun getCampsiteDetail(@Path("campsiteId") campsiteId: String): SearchDetailResponse
}