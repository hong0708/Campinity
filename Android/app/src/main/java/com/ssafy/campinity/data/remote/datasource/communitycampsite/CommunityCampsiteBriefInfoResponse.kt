package com.ssafy.campinity.data.remote.datasource.communitycampsite

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo

data class CommunityCampsiteBriefInfoResponse(
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("campsiteName")
    val campsiteName: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("latitude")
    val longitude: String
) : DataToDomainMapper<CampsiteBriefInfo> {
    override fun toDomainModel(): CampsiteBriefInfo {
        return CampsiteBriefInfo(
            campsiteId,
            campsiteName,
            address,
            latitude,
            longitude
        )
    }
}