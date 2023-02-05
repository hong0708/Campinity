package com.ssafy.campinity.data.remote.datasource.home

import com.ssafy.campinity.data.remote.datasource.collection.CollectionResponse

interface HomeRemoteDataSource {

    suspend fun getHomeBanners(): List<HomeBannerResponse>

    suspend fun getHomeCollections(): List<CollectionResponse>
}