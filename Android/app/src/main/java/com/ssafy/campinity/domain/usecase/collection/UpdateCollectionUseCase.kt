package com.ssafy.campinity.domain.usecase.collection

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import com.ssafy.campinity.domain.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
){
    suspend fun updateCollection(
        collectionId: String,
        campsiteName: String,
        content: String,
        date: String,
        file: MultipartBody.Part?
    ): Resource<CollectionItem> = withContext(Dispatchers.IO) {
        collectionRepository.updateCollection(
            collectionId, campsiteName, content, date, file
        )
    }

    suspend fun updateCollectionWithoutImg(
        collectionId: String,
        campsiteName: String,
        content: String,
        date: String
    ): Resource<CollectionItem> = withContext(Dispatchers.IO) {
        collectionRepository.updateCollectionWithoutImg(
            collectionId, campsiteName, content, date
        )
    }
}