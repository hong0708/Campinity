package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo
import okhttp3.MultipartBody

interface CommunityRepository {

    suspend fun getCampsiteBriefInfoByCampName(campsiteName: String)
            : Resource<List<CampsiteBriefInfo>>

    suspend fun getCampsiteBriefInfoByUserLocation(
        bottomRightLat: Double,
        bottomRightLng: Double,
        topLeftLat: Double,
        topLeftLng: Double
    ): Resource<List<CampsiteBriefInfo>>

    suspend fun getCampsiteMessageBriefInfoBYScope(
        bottomRightLat: Double,
        bottomRightLng: Double,
        campsiteName: String,
        topLeftLat: Double,
        topLeftLng: Double
    ): Resource<List<CampsiteMessageBriefInfo>>

    suspend fun createCampsiteMessage(
        messageCategory: String,
        file: MultipartBody.Part?,
        latitude: Double,
        campsiteId: String,
        content: String,
        longitude: Double,
    ): Resource<CampsiteMessageDetailInfo>
}