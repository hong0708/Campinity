package com.ssafy.campinity.data.remote.datasource.home

import com.ssafy.campinity.data.remote.datasource.collection.CollectionResponse
import com.ssafy.campinity.data.remote.service.HomeApiService
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeApiService: HomeApiService
) : HomeRemoteDataSource {

    override suspend fun getHomeBanners(): List<HomeBannerResponse> =
        homeApiService.getHomeBanners()

    override suspend fun getHomeCollections(): List<CollectionResponse> =
        homeApiService.getHomeCollections()

    override suspend fun getHighestCampsite(): List<RankingCampsiteResponse> =
        homeApiService.getHighestCampsite()

    override suspend fun getHottestCampsite(): List<RankingCampsiteResponse> =
        homeApiService.getHottestCampsite()
}