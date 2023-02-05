package com.ssafy.campinity.domain.usecase.search

import com.ssafy.campinity.domain.repository.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCampsiteDetailUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(campsiteId: String) = searchRepository.getCampsiteDetail(campsiteId)
}