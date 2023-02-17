package com.ssafy.campinity.domain.entity.search

data class CampsiteNoteBriefInfo(
    val messageId: String,
    val campsiteName: String,
    val campsiteId: String,
    val messageCategory: String,
    val content: String,
    val latitude: Double,
    val longitude: Double
)
