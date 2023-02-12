package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterRequest
import com.ssafy.campinity.data.remote.datasource.search.SearchRemoteDataSource
import com.ssafy.campinity.data.remote.datasource.search.SearchReviewRequest
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.search.CampsiteDetailInfo
import com.ssafy.campinity.domain.entity.search.CampsiteNoteBriefInfo
import com.ssafy.campinity.domain.entity.search.Review
import com.ssafy.campinity.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun getCampsitesByFiltering(filter: SearchFilterRequest): Resource<List<CampsiteBriefInfo>> =
        wrapToResource(Dispatchers.IO) {
            searchRemoteDataSource.getCampsitesByFiltering(filter).map { it.toDomainModel() }
        }

    override suspend fun getCampsitesByScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ): Resource<List<CampsiteBriefInfo>> = wrapToResource(Dispatchers.IO) {
        searchRemoteDataSource.getCampsitesByScope(
            bottomRightLat,
            bottomRightLng,
            topLeftLat,
            topLeftLng
        ).map { it.toDomainModel() }
    }

    override suspend fun getCampsiteDetail(campsiteId: String): Resource<CampsiteDetailInfo> =
        wrapToResource(Dispatchers.IO) {
            searchRemoteDataSource.getCampsiteDetail(campsiteId).toDomainModel()
        }

    override suspend fun getCampsiteReviewNotes(
        campsiteId: String,
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double,
    ): Resource<List<CampsiteNoteBriefInfo>> = wrapToResource(Dispatchers.IO) {
        searchRemoteDataSource.getCampsiteReviewNotes(
            campsiteId,
            bottomRightLat,
            bottomRightLng,
            topLeftLat,
            topLeftLng,
        ).map { it.toDomainModel() }
    }

    override suspend fun writeReview(review: SearchReviewRequest): Resource<Review> =
        wrapToResource(Dispatchers.IO) {
            searchRemoteDataSource.writeReview(review).toDomainModel()
        }

    override suspend fun deleteReview(reviewId: String): Resource<String> =
        wrapToResource(Dispatchers.IO) {
            searchRemoteDataSource.deleteReview(reviewId).toDomainModel()
        }
}