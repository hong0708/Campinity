package com.ssafy.campinity.data.remote.datasource.CommunityCampsite

interface CommunityRemoteDataSource {

    suspend fun getCampsiteBriefInfoByCampName(campsiteName: String)
            : List<CommunityCampsiteBriefInfoResponse>

    suspend fun getCampsiteBriefInfoByUserLocation(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ): List<CommunityCampsiteBriefInfoResponse>

    suspend fun getCampsiteMessagesByScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        campsiteId: String,
        topLeftLat: Double,
        topLeftLng: Double
    ): List<CommunityCampsiteMessageResponse>
}