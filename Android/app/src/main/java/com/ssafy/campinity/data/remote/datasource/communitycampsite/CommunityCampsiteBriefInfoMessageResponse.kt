package com.ssafy.campinity.data.remote.datasource.communitycampsite

import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.community.CampsiteMessageBriefInfo

data class CommunityCampsiteBriefInfoMessageResponse(
    val authorName: String?,
    val campsiteName: String,
    val content: String,
    val countLikes: Int,
    val createdAt: String,
    val etcValidDate: String,
    val imagePath: String?,
    val latitude: String,
    val likeCheck: Boolean,
    val longitude: String,
    val messageCategory: String,
    val messageId: String,
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
