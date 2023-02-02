package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.home.HomeBanner

interface HomeRepository {

    suspend fun getHomeBanners(): Resource<List<HomeBanner>>
}