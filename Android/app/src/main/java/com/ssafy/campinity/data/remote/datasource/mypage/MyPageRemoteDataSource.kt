package com.ssafy.campinity.data.remote.datasource.mypage

interface MyPageRemoteDataSource {

    suspend fun getNotes(): MyPageNoteResponse

    suspend fun getUserInfo(): MyPageUserResponse
}