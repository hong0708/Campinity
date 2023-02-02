package com.ssafy.campinity.domain.entity.search

data class AreaSido(
    val sido: String,
    val gugunList: List<AreaGugun>,
    val campsiteCountAll: Int
)
