package com.ssafy.campinity.data.remote.datasource.mypage

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.mypage.ScrapCampsite

data class ScrapCampsiteResponse(
    @SerializedName("campName")
    val campName: String,
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("doName")
    val doName: String,
    @SerializedName("firstImageUrl")
    val firstImageUrl: String,
    @SerializedName("sigunguName")
    val sigunguName: String
) : DataToDomainMapper<ScrapCampsite> {
    override fun toDomainModel(): ScrapCampsite =
        ScrapCampsite(campName, campsiteId, doName, firstImageUrl, sigunguName)
}