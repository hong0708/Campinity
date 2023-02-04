package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.CommunityCampsite.CommunityCampsiteBriefInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityApiService {

    @GET("/api/v1/campsites/search")
    suspend fun getCommunityCampsiteBriefInfoByCampName(@Query("keyword") campsiteName: String)
            : List<CommunityCampsiteBriefInfoResponse>

    @GET("/api/v1/campsites/scope/meta")
    suspend fun getCommunityCampsiteBriefInfoByUserLocation(
        @Query("bottomRightLat") bottomRightLat: Double,
        @Query("bottomRightLng") bottomRightLng: Double,
        @Query("topLeftLat") topLeftLat: Double,
        @Query("topLeftLng") topLeftLng: Double
    ): List<CommunityCampsiteBriefInfoResponse>
}