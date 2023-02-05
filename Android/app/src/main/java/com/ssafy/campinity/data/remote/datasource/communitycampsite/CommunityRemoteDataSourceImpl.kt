package com.ssafy.campinity.data.remote.datasource.communitycampsite

import com.ssafy.campinity.data.remote.service.CommunityApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
    ): List<CommunityCampsiteBriefInfoMessageResponse> =
        communityApiService.getCampsiteMessagesByScope(
            campsiteId,
            bottomRightLat,
            bottomRightLng,
            topLeftLat,
            topLeftLng
        )

    override suspend fun createCampsiteMessage(body: CommunityCampsiteMessageRequest)
            : CommunityCampsiteDetailInfoMessageResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["messageCategory"] =
            body.messageCategory.toRequestBody("text/plain".toMediaTypeOrNull())
        map["latitude"] = body.latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        map["campsiteId"] = body.campsiteId.toRequestBody("text/plain".toMediaTypeOrNull())
        map["content"] = body.content.toRequestBody("text/plain".toMediaTypeOrNull())
        map["longitude"] = body.longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        return communityApiService.createCampsiteMessage(map, body.file)
    }
}