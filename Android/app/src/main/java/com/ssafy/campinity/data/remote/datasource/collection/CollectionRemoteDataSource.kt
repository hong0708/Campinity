package com.ssafy.campinity.data.remote.datasource.collection

interface CollectionRemoteDataSource {

    suspend fun getCollections(): List<CollectionResponse>

    suspend fun createCollection(body: CollectionRequest): CollectionResponse

    suspend fun getCollection(collectionId: String): CollectionResponse

    suspend fun deleteCollection(collectionId: String): String

    suspend fun updateCollection(collectionId: String, body: CollectionRequest): CollectionResponse

    suspend fun updateCollectionWithoutImg(
        collectionId: String,
        body: CollectionRequest
    ): CollectionResponse
}