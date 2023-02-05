package com.ssafy.campinity.data.remote.datasource.communitycampsite

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo

data class CommunityCampsiteBriefInfoMessageResponse(
    @SerializedName("authorName")
    val authorName: String?,
    @SerializedName("campsiteName")
    val campsiteName: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("countLikes")
    val countLikes: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("etcValidDate")
    val etcValidDate: String,
    @SerializedName("imagePath")
    val imagePath: String?,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("likeCheck")
    val likeCheck: Boolean,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("messageCategory")
    val messageCategory: String,
    @SerializedName("messageId")
    val messageId: String,
    @SerializedName("updatedAt")
    val updatedAt: String
) : DataToDomainMapper<CampsiteMessageBriefInfo> {
    override fun toDomainModel(): CampsiteMessageBriefInfo {
        return CampsiteMessageBriefInfo(
            authorName,
            campsiteName,
            content,
            countLikes,
            createdAt,
            etcValidDate,
            imagePath,
            latitude,
            likeCheck,
            longitude,
            messageCategory,
            messageId,
            updatedAt
        )
    }
}
