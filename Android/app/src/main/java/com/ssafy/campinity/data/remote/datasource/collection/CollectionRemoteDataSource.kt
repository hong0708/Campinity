package com.ssafy.campinity.data.remote.datasource.collection

interface CollectionRemoteDataSource {

    suspend fun getCollections(): List<CollectionResponse>

    suspend fun createCollection(body: CollectionRequest): CollectionResponse
}