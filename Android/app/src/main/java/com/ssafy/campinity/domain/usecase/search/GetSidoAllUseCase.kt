package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.search.SidoItem
import com.ssafy.campinity.domain.repository.AreaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSidoAllUseCase @Inject constructor(
    private val areaRepository: AreaRepository
) {
    suspend operator fun invoke(): Resource<List<SidoItem>> =
        withContext(Dispatchers.IO){
            areaRepository.getSidoAll()
        }
}