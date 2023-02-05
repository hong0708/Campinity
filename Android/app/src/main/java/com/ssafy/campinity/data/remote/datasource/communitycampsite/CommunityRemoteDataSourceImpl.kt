package com.ssafy.campinity.data.remote.datasource.communitycampsite

import com.ssafy.campinity.data.remote.service.CommunityApiService
import javax.inject.Inject

class CommunityRemoteDataSourceImpl @Inject constructor(
    private val communityApiService: CommunityApiService
) : CommunityRemoteDataSource {

    override suspend fun getCampsiteBriefInfoByCampName(campsiteName: String)
            : List<CommunityCampsiteBriefInfoResponse> =
        communityApiService.getCommunityCampsiteBriefInfoByCampName(campsiteName)

    override suspend fun getCampsiteBriefInfoByUserLocation(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ): List<CommunityCampsiteBriefInfoResponse> =
        communityApiService.getCommunityCampsiteBriefInfoByUserLocation(
            bottomRightLat,
            bottomRightLng,
            topLeftLat,
            topLeftLng
        )

    override suspend fun getCampsiteMessagesBriefInfoByScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        campsiteId: String,
        topLeftLat: Double,
        topLeftLng: Double
    ): List<CommunityCampsiteBriefInfoMessageResponse> = communityApiService.getCampsiteMessagesByScope(
        campsiteId,
        bottomRightLat,
        bottomRightLng,
        topLeftLat,
        topLeftLng
    )
}