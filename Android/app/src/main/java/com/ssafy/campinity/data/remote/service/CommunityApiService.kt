package com.ssafy.campinity.data.remote.service

import com.ssafy.campinity.data.remote.datasource.communitycampsite.CommunityCampsiteBriefInfoResponse
import com.ssafy.campinity.data.remote.datasource.communitycampsite.CommunityCampsiteBriefInfoMessageResponse
import com.ssafy.campinity.data.remote.datasource.communitycampsite.CommunityCampsiteDetailInfoMessageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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

    @GET("/api/v2/messages/{campsiteId}/scope")
    suspend fun getCampsiteMessagesByScope(
        @Path("campsiteId") campsiteId: String,
        @Query("bottomRightLat") bottomRightLat: Double,
        @Query("bottomRightLng") bottomRightLng: Double,
        @Query("topLeftLat") topLeftLat: Double,
        @Query("topLeftLng") topLeftLng: Double
    ): List<CommunityCampsiteBriefInfoMessageResponse>

    @POST("/api/v2/messages")
    suspend fun createCampsiteMessage(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part?
    ):CommunityCampsiteDetailInfoMessageResponse
}