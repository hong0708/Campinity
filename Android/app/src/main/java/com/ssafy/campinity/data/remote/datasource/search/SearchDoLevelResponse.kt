package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.search.ClusteringDo

data class SearchDoLevelResponse(
    @SerializedName("cnt")
    var cnt: Int,
    @SerializedName("doName")
    var doName: String,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double
) : DataToDomainMapper<ClusteringDo> {
    override fun toDomainModel(): ClusteringDo = ClusteringDo(
        cnt,
        doName,
        latitude,
        longitude
    )
}