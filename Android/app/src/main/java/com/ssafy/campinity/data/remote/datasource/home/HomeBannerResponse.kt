package com.ssafy.campinity.data.remote.datasource.home

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.home.HomeBanner

data class HomeBannerResponse(
    @SerializedName("curationId")
    val curationId: String,
    @SerializedName("firstImagePath")
    val firstImagePath: String,
    @SerializedName("title")
    val title: String
) : DataToDomainMapper<HomeBanner> {
    override fun toDomainModel(): HomeBanner =
        HomeBanner(curationId, firstImagePath, title)
}
