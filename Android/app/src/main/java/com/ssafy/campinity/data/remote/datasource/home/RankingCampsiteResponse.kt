package com.ssafy.campinity.data.remote.datasource.home

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.home.RankingCampsiteItem

data class RankingCampsiteResponse(
    @SerializedName("campName")
    val campName: String,
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("doName")
    val doName: String,
    @SerializedName("firstImageUrl")
    val firstImageUrl: String,
    @SerializedName("ranking")
    val ranking: Int,
    @SerializedName("sigunguName")
    val sigunguName: String
) : DataToDomainMapper<RankingCampsiteItem> {
    override fun toDomainModel(): RankingCampsiteItem =
        RankingCampsiteItem(campName, campsiteId, doName, firstImageUrl, ranking, sigunguName)
}
