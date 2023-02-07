package com.ssafy.campinity.data.remote.datasource.note

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.search.CampsiteNoteBriefInfo

data class NoteBriefResponse(
    @SerializedName("messageId")
    val messageId: String,
    @SerializedName("campsiteName")
    val campsiteName: String,
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("messageCategory")
    val messageCategory: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
) : DataToDomainMapper<CampsiteNoteBriefInfo> {
    override fun toDomainModel(): CampsiteNoteBriefInfo =
        CampsiteNoteBriefInfo(
            messageId,
            campsiteName,
            campsiteId,
            messageCategory,
            content,
            latitude,
            longitude,
        )
}