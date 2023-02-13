package com.ssafy.campinity.domain.entity.home

data class RankingCampsiteItem(
    val campName: String,
    val campsiteId: String,
    val doName: String,
    val firstImageUrl: String,
    val ranking: Int,
    val sigunguName: String
)