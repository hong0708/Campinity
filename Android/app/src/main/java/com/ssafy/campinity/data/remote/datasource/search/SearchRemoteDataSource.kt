package com.ssafy.campinity.data.remote.datasource.search

import com.ssafy.campinity.data.remote.datasource.note.NoteBriefResponse

interface SearchRemoteDataSource {

    suspend fun getCampsitesByFiltering(filter: SearchFilterRequest): List<SearchBriefResponse>

    suspend fun getCampsitesByScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ): List<SearchBriefResponse>

    suspend fun getCampsiteDetail(campsiteId: String): SearchDetailResponse

    suspend fun getCampsiteReviewNotes(
        campsiteId: String,
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double,
    ): List<NoteBriefResponse>

    suspend fun writeReview(review: SearchReviewRequest): SearchReviewResponse

    suspend fun deleteReview(reviewId: String): SearchDeleteReviewResponse
}