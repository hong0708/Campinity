package com.ssafy.campinity.data.local.datasource.area

import com.google.gson.annotations.SerializedName

data class AreaSidoResponse(
    @SerializedName("sido_name")
    val sidoName: String,
    @SerializedName("campsite_count_all")
    val campsiteCountAll: Int
)
