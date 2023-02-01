package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.home.HomeBannerResponse
import retrofit2.http.GET

interface HomeApiService {

    @GET("/api/v7/curations/banners")
    suspend fun getHomeBanners(): List<HomeBannerResponse>
}