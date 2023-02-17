package com.ssafy.campinity.domain.entity.community

data class CampsiteMessageBriefInfo(
    val campsiteId: String?,
    val campsiteName: String,
    val content: String,
    val latitude: String,
    val longitude: String,
    val messageCategory: String,
    val messageId: String
)
