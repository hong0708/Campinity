package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.mypage.MyPageNote

interface MyPageRepository {

    suspend fun getNotes(): Resource<MyPageNote>
}