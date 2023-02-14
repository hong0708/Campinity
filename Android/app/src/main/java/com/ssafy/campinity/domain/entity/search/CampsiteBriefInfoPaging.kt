package com.ssafy.campinity.domain.entity.search

data class CampsiteBriefInfoPaging(
    val currentPage: Int,
    val data: CampsiteBriefInfo,
    val maxPage: Int
)
