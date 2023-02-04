package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.collection.CollectionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface CollectionApiService {

    @GET("/api/v5/my-collections")
    suspend fun getCollections(): List<CollectionResponse>

    @Multipart
    @POST("/api/v5/my-collections")
    suspend fun createCollection(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part?
    ): CollectionResponse

    @GET("/api/v5/my-collections/{collectionId}")
    suspend fun getCollection(@Path("collectionId") collectionId: String): CollectionResponse

    @DELETE("/api/v5/my-collections/{collectionId}")
    suspend fun deleteCollection(@Path("collectionId") collectionId: String): String

    @Multipart
    @POST("/api/v5/my-collections/{collectionId}")
    suspend fun updateCollection(
        @Path("collectionId") collectionId: String,
        @Query("campsiteName") campsiteName: String,
        @Query("content") content: String,
        @Query("date") date: String,
        @Part file: MultipartBody.Part?
    ): CollectionResponse
}