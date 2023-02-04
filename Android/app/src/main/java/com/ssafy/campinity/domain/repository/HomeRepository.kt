package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import com.ssafy.campinity.domain.entity.home.HomeBanner

interface HomeRepository {

    suspend fun getHomeBanners(): Resource<List<HomeBanner>>

    suspend fun getHomeCollections(): Resource<List<CollectionItem>>
}