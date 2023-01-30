package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.collection.CollectionResponse
import retrofit2.http.GET

interface CollectionApiService {

    @GET("/api/v5/my-collections")
    suspend fun getCollections(): List<CollectionResponse>
}