package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.search.ClusteringSiGunGu

data class SearchSiGunGuLevelResponse(
    @SerializedName("cnt")
    var cnt: Int,
    @SerializedName("sigunguName")
    var sigunguName: String,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double
): DataToDomainMapper<ClusteringSiGunGu> {
    override fun toDomainModel(): ClusteringSiGunGu = ClusteringSiGunGu(
        cnt,
        latitude,
        longitude,
        sigunguName
    )
}