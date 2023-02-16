package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.search.*
import com.ssafy.campinity.domain.entity.search.*
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

    override suspend fun scrapCampsite(campsiteId: String): Resource<Boolean> =
        wrapToResource(Dispatchers.IO) {
            searchRemoteDataSource.scrapCampsite(campsiteId).toDomainModel()
        }

    override suspend fun getCampsitesByDo(filter: SearchFilterClusteringRequest): Resource<List<ClusteringDo>> =
        wrapToResource(Dispatchers.IO) {
            searchRemoteDataSource.getCampsitesByDo(filter).map { it.toDomainModel() }
        }

    override suspend fun getCampsitesBySiGunGu(filter: SearchFilterClusteringRequest): Resource<List<ClusteringSiGunGu>> =
        wrapToResource(Dispatchers.IO) {
            searchRemoteDataSource.getCampsitesBySiGunGu(filter).map { it.toDomainModel() }
        }
}