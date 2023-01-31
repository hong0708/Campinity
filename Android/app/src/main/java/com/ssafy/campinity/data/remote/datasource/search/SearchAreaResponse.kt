package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo

class SearchAreaResponse(
    @SerializedName("campName")
    val campName: String,
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("doName")
    val doName: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("isScraped")
    val isScraped: Boolean,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("messageCnt")
    val messageCnt: Int,
    @SerializedName("sigunguName")
    val sigunguName: String
) : DataToDomainMapper<CampsiteBriefInfo> {
    override fun toDomainModel(): CampsiteBriefInfo = CampsiteBriefInfo(
        campsiteId,
        campName,
        "$doName $sigunguName",
        messageCnt,
        images,
        isScraped,
        latitude,
        longitude
    )

}