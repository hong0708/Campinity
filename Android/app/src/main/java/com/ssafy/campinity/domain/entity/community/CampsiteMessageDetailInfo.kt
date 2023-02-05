package com.ssafy.campinity.domain.entity.community

data class CampsiteMessageDetailInfo(
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
)
