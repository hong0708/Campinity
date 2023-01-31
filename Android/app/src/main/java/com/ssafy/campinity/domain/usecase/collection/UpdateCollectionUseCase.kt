package com.ssafy.campinity.domain.usecase.collection

import com.ssafy.campinity.domain.repository.CollectionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
){
    suspend operator fun invoke(collectionId: String) =
        collectionRepository.updateCollection(collectionId)
}