package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.collection.CollectionRemoteDataSource
import com.ssafy.campinity.data.remote.datasource.collection.CollectionRequest
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import com.ssafy.campinity.domain.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionRemoteDataSource: CollectionRemoteDataSource
) : CollectionRepository {

    override suspend fun getCollections(): Resource<List<CollectionItem>> =
        wrapToResource(Dispatchers.IO) {
            collectionRemoteDataSource.getCollections().map { it.toDomainModel() }
        }

    override suspend fun createCollection(
        campsiteName: String,
        content: String,
        date: String,
        file: MultipartBody.Part?
    ): Resource<CollectionItem> =
        wrapToResource(Dispatchers.IO) {
            collectionRemoteDataSource.createCollection(
                CollectionRequest(
                    campsiteName, content, date, file
                )
            ).toDomainModel()
        }

    override suspend fun getCollection(collectionId: String): Resource<CollectionItem> =
        wrapToResource(Dispatchers.IO) {
            collectionRemoteDataSource.getCollection(collectionId).toDomainModel()
    }
}