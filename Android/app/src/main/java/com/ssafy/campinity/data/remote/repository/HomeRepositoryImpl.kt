package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.home.HomeRemoteDataSource
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import com.ssafy.campinity.domain.entity.home.HomeBanner
import com.ssafy.campinity.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {

    override suspend fun getHomeBanners(): Resource<List<HomeBanner>> =
        wrapToResource(Dispatchers.IO) {
            homeRemoteDataSource.getHomeBanners().map { it.toDomainModel() }
        }

    override suspend fun getHomeCollections(): Resource<List<CollectionItem>> =
        wrapToResource(Dispatchers.IO) {
            homeRemoteDataSource.getHomeCollections().map { it.toDomainModel() }
        }
}