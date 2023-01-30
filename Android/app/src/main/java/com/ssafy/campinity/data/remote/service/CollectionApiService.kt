package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.collection.CollectionResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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
}