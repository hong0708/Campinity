package com.ssafy.campinity.data.remote.datasource.collection

import com.ssafy.campinity.data.remote.service.CollectionApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CollectionRemoteDataSourceImpl @Inject constructor(
    private val collectionApiService: CollectionApiService
) : CollectionRemoteDataSource {

    override suspend fun getCollections(): List<CollectionResponse> =
        collectionApiService.getCollections()

    override suspend fun createCollection(body: CollectionRequest): CollectionResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["campsiteName"] = body.campsiteName.toRequestBody("text/plain".toMediaTypeOrNull())
        map["content"] = body.content.toRequestBody("text/plain".toMediaTypeOrNull())
        map["date"] = body.date.toRequestBody("text/plain".toMediaTypeOrNull())
        return collectionApiService.createCollection(map, body.file)
    }

    override suspend fun getCollection(collectionId: String): CollectionResponse =
        collectionApiService.getCollection(collectionId)

    override suspend fun deleteCollection(collectionId: String): String =
        collectionApiService.deleteCollection(collectionId)

    override suspend fun updateCollection(
        collectionId: String,
        body: CollectionRequest
    ): CollectionResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["campsiteName"] = body.campsiteName.toRequestBody("text/plain".toMediaTypeOrNull())
        map["content"] = body.content.toRequestBody("text/plain".toMediaTypeOrNull())
        map["date"] = body.date.toRequestBody("text/plain".toMediaTypeOrNull())
        return collectionApiService.updateCollection(collectionId, map, body.file)
    }

    override suspend fun updateCollectionWithoutImg(
        collectionId: String,
        body: CollectionRequest
    ): CollectionResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["campsiteName"] = body.campsiteName.toRequestBody("text/plain".toMediaTypeOrNull())
        map["content"] = body.content.toRequestBody("text/plain".toMediaTypeOrNull())
        map["date"] = body.date.toRequestBody("text/plain".toMediaTypeOrNull())
        return collectionApiService.updateCollection(collectionId, map, null)
    }
}