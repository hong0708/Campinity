package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.note.NoteBriefResponse
import com.ssafy.campinity.data.remote.datasource.search.*
import retrofit2.http.*

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

    @GET("/api/v2/messages/{campsiteId}/scope")
    suspend fun getCampsiteReviewNotes(
        @Path("campsiteId") campsiteId: String,
        @Query("bottomRightLat") bottomRightLat: Double,
        @Query("bottomRightLng") bottomRightLng: Double,
        @Query("topLeftLat") topLeftLat: Double,
        @Query("topLeftLng") topLeftLng: Double,
    ): List<NoteBriefResponse>

    @POST("/api/v3/reviews")
    suspend fun writeReview(@Body body: SearchReviewRequest): SearchReviewResponse

    @DELETE("/api/v3/reviews/{reviewId}")
    suspend fun deleteReview(
        @Path("reviewId") reviewId: String
    ): SearchDeleteReviewResponse

    @PUT("/api/v1/campsites/scraps/{campsiteId}")
    suspend fun scrapCampsite(
        @Path("campsiteId") campsiteId: String
    ): SearchScrapResponse

    @GET("/api/v1/campsites/conditions/do-level")
    suspend fun getCampsitesByDo(
        @Query("allowAnimal") allowAnimal: String?,
        @Query("amenity") amenity: String?,
        @Query("doName") doName: String?,
        @Query("fclty") fclty: String?,
        @Query("industry") industry: String?,
        @Query("keyword") keyword: String?,
        @Query("openSeason") openSeason: String?,
        @Query("sigunguName") sigunguName: String?,
        @Query("theme") theme: String?,
    ): List<SearchDoLevelResponse>

    @GET("/api/v1/campsites/conditions/sigungu-level")
    suspend fun getCampsitesBySiGunGu(
        @Query("allowAnimal") allowAnimal: String?,
        @Query("amenity") amenity: String?,
        @Query("doName") doName: String?,
        @Query("fclty") fclty: String?,
        @Query("industry") industry: String?,
        @Query("keyword") keyword: String?,
        @Query("openSeason") openSeason: String?,
        @Query("sigunguName") sigunguName: String?,
        @Query("theme") theme: String?,
    ): List<SearchSiGunGuLevelResponse>
}