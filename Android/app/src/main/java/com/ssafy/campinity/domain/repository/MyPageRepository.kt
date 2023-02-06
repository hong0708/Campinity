package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.mypage.MyPageNote
import com.ssafy.campinity.domain.entity.mypage.MyPageUser

interface MyPageRepository {

    suspend fun getNotes(): Resource<MyPageNote>

    suspend fun getUserInfo(): Resource<MyPageUser>
}