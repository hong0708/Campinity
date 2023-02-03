package com.ssafy.campinity.domain.entity.search

data class AreaSido(
    var sido: String,
    var gugunList: List<AreaGugun>,
    var campsiteCountAll: Int
)
