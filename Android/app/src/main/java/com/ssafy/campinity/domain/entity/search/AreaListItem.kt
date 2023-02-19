package com.ssafy.campinity.domain.entity.search

data class AreaListItem(
    val sidoName: String,
    val gugunList: List<GugunItem>,
    val campsiteCountAll: Int
)
