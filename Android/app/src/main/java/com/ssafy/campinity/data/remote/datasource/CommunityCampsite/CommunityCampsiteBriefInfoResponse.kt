package com.ssafy.campinity.data.remote.datasource.CommunityCampsite

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.community.CampsiteBriefInfo

data class CommunityCampsiteBriefInfoResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("campName")
    val campName: String,
    @SerializedName("campsiteId")
    val campsiteId: String
) : DataToDomainMapper<CampsiteBriefInfo> {
    override fun toDomainModel(): CampsiteBriefInfo {
        return CampsiteBriefInfo(
            address,
            campName,
            campsiteId
        )
    }
}