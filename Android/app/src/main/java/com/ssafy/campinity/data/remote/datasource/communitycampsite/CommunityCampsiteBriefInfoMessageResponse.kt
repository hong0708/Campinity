package com.ssafy.campinity.data.remote.datasource.communitycampsite

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo

data class CommunityCampsiteBriefInfoMessageResponse(
    @SerializedName("campsiteId")
    val campsiteId: String?,
    @SerializedName("campsiteName")
    val campsiteName: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("messageCategory")
    val messageCategory: String,
    @SerializedName("messageId")
    val messageId: String,
) : DataToDomainMapper<CampsiteMessageBriefInfo> {
    override fun toDomainModel(): CampsiteMessageBriefInfo {
        return CampsiteMessageBriefInfo(
            campsiteId,
            campsiteName,
            content,
            latitude,
            longitude,
            messageCategory,
            messageId,
        )
    }
}
