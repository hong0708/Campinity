package com.ssafy.campinity.domain.usecase.home

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.home.RankingCampsiteItem
import com.ssafy.campinity.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRankingCampsiteUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun getHighestCampsites(): Resource<List<RankingCampsiteItem>> = withContext(Dispatchers.IO) {
        homeRepository.getHighestCampsite()
    }

    suspend fun getHottestCampsites(): Resource<List<RankingCampsiteItem>> = withContext(Dispatchers.IO) {
        homeRepository.getHottestCampsite()
    }
}