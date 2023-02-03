package com.ssafy.campinity.data.local.datasource.area

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.search.GugunItem

data class AreaGugunResponse(
    @SerializedName("gugun_name")
    val gugunName: String,
    @SerializedName("campsite_count")
    val campsiteCount: Int
) : DataToDomainMapper<GugunItem> {

    override fun toDomainModel(): GugunItem = GugunItem(
        gugunName,
        campsiteCount
    )
}