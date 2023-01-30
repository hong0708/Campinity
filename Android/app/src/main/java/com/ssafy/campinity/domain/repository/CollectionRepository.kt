package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import okhttp3.MultipartBody

interface CollectionRepository {

    suspend fun getCollections(): Resource<List<CollectionItem>>

    suspend fun createCollection(
        campsiteName: String,
        content: String,
        date: String,
        file: MultipartBody.Part?
    ): Resource<CollectionItem>
}