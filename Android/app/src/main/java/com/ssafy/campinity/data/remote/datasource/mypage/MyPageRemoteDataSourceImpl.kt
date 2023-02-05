package com.ssafy.campinity.data.remote.datasource.mypage

import com.ssafy.campinity.data.remote.service.MyPageApiService
import javax.inject.Inject

class MyPageRemoteDataSourceImpl @Inject constructor(
    private val myPageApiService: MyPageApiService
) : MyPageRemoteDataSource {

    override suspend fun getNotes(): MyPageNoteResponse =
        myPageApiService.getNotes()
}