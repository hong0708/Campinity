package com.ssafy.campinity.data.local.datasource.area

import com.google.gson.annotations.SerializedName

data class AreaGugunResponse(
    @SerializedName("gugun_name")
    val gugunName: String,
    @SerializedName("campsite_count")
    val campsiteCount: Int
)