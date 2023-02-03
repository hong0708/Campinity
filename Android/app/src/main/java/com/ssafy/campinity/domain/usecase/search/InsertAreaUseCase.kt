package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.domain.entity.search.AreaEntity
import com.ssafy.campinity.domain.repository.AreaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertAreaUseCase @Inject constructor(
    private val areaRepository: AreaRepository
) {
    suspend operator fun invoke(areaEntity: AreaEntity) = withContext(Dispatchers.IO) {
        areaRepository.insert(areaEntity)
    }
}