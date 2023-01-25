package com.ssafy.campinity.domain.entity.search

data class CampsiteBriefInfo(
    val campsiteId: String,
    val name: String,
    val area: String,
    val noteCount: Int,
    val images: List<String>
)
