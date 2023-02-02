package com.ssafy.campinity.data.remote.datasource.home

interface HomeRemoteDataSource {

    suspend fun getHomeBanners(): List<HomeBannerResponse>
}