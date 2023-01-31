package com.ssafy.campinity.domain.usecase.collection

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import com.ssafy.campinity.domain.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCollectionDetailUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke(collectionId: String): Resource<CollectionItem> =
        withContext(Dispatchers.IO) {
            collectionRepository.getCollection(collectionId)
        }
}