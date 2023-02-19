package com.ssafy.campinity.domain.usecase.mypage

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.mypage.LogoutRequest
import com.ssafy.campinity.domain.repository.MyPageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestLogoutUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(
        body: LogoutRequest
    ): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            myPageRepository.requestLogout(body)
        }
}