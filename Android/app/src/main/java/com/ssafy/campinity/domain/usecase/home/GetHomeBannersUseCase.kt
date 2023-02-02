package com.ssafy.campinity.domain.usecase.home

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.home.HomeBanner
import com.ssafy.campinity.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHomeBannersUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Resource<List<HomeBanner>> = withContext(Dispatchers.IO) {
        homeRepository.getHomeBanners()
    }
}