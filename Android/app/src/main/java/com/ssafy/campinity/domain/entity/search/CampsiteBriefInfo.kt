package com.ssafy.campinity.domain.entity.search

data class CampsiteBriefInfo(
    val campsiteId: String,
    val campName: String,
    val doName: String,
    val sigunguName: String,
    val messageCnt: Int,
    val images: List<String>,
    val thumbnails: List<String>,
    var isScraped: Boolean,
    val latitude: Double,
    val longitude: Double
)
