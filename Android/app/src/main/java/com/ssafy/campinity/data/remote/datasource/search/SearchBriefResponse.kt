package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfoPaging
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo

data class SearchBriefResponse(
    @SerializedName("currentPage")
    private val currentPage: Int,
    @SerializedName("data")
    private val data: CampsiteBriefInfo,
    @SerializedName("maxPage")
    private val maxPage: Int
) : DataToDomainMapper<CampsiteBriefInfoPaging> {
    override fun toDomainModel(): CampsiteBriefInfoPaging =
        CampsiteBriefInfoPaging(currentPage, data, maxPage)
}