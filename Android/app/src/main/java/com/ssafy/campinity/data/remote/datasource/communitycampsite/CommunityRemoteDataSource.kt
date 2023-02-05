package com.ssafy.campinity.data.remote.datasource.communitycampsite

interface CommunityRemoteDataSource {

    suspend fun getCampsiteBriefInfoByCampName(campsiteName: String)
            : List<CommunityCampsiteBriefInfoResponse>

    suspend fun getCampsiteBriefInfoByUserLocation(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ): List<CommunityCampsiteBriefInfoResponse>

    suspend fun getCampsiteMessagesBriefInfoByScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        campsiteId: String,
        topLeftLat: Double,
        topLeftLng: Double
    ): List<CommunityCampsiteBriefInfoMessageResponse>

    suspend fun createCampsiteMessage(
        body: CommunityCampsiteMessageRequest
    ): CommunityCampsiteDetailInfoMessageResponse

    suspend fun getCampsiteMessageDetailInfo(
        messageId: String
    ): CommunityCampsiteDetailInfoMessageResponse
}