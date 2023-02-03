package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.search.GugunItem
import com.ssafy.campinity.domain.repository.AreaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGugunUseCase @Inject constructor(
    private val areaRepository: AreaRepository
) {
    suspend operator fun invoke(sidoName: String): Resource<List<GugunItem>> =
        withContext(Dispatchers.IO) {
            areaRepository.getGugun(sidoName)
        }
}