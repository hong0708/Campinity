package com.ssafy.campinity.domain.usecase.mypage

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.mypage.ScrapCampsite
import com.ssafy.campinity.domain.repository.MyPageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetScrapCampsitesUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(): Resource<List<ScrapCampsite>> = withContext(Dispatchers.IO) {
        myPageRepository.getScrapCampsites()
    }
}