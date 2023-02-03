package com.ssafy.campinity.domain.entity.search

import com.google.gson.annotations.SerializedName

data class GugunItem(
    @SerializedName("gugun_name")
    val gugun: String,
    @SerializedName("campsite_count")
    val campsiteCount: Int
)
