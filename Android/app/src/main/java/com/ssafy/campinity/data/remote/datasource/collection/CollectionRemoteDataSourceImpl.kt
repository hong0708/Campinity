package com.ssafy.campinity.data.remote.datasource.collection

import com.ssafy.campinity.data.remote.service.CollectionApiService
import javax.inject.Inject

class CollectionRemoteDataSourceImpl @Inject constructor(
    private val collectionApiService: CollectionApiService
) : CollectionRemoteDataSource {

    override suspend fun getCollections(): List<CollectionResponse> =
        collectionApiService.getCollections()

    override suspend fun createCollection(body: CollectionRequest): CollectionResponse =
        collectionApiService.collectionCollection(
            body.campsiteName,
            body.content,
            body.date,
            body.file
        )

    override suspend fun getCollection(collectionId: String): CollectionResponse =
        collectionApiService.getCollection(collectionId)

    override suspend fun deleteCollection(collectionId: String): String =
        collectionApiService.deleteCollection(collectionId)

    override suspend fun updateCollection(collectionId: String): CollectionResponse =
        collectionApiService.updateCollection(collectionId)
}