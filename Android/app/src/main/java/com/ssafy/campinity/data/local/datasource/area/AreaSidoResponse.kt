package com.ssafy.campinity.data.local.datasource.area

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.search.SidoItem

data class AreaSidoResponse(
    @SerializedName("sido_name") val sidoName: String,
    @SerializedName("campsite_count_all") val campsiteCountAll: Int
) : DataToDomainMapper<SidoItem> {

    override fun toDomainModel(): SidoItem = SidoItem(sidoName, campsiteCountAll)
}
