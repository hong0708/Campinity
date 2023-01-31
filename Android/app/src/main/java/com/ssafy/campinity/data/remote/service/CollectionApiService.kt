package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.collection.CollectionResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface CollectionApiService {

    @GET("/api/v5/my-collections")
    suspend fun getCollections(): List<CollectionResponse>

    @Multipart
    @POST("/api/v5/my-collections")
    suspend fun collectionCollection(
        @Query("campsiteName") campsiteName: String,
        @Query("content") content: String,
        @Query("date") date: String,
        @Part file: MultipartBody.Part?
    ): CollectionResponse

    @GET("/api/v5/my-collections/{collectionId}")
    suspend fun getCollection(@Path("collectionId") collectionId: String): CollectionResponse
}