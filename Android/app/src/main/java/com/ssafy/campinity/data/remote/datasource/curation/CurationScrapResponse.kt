package com.ssafy.campinity.data.remote.datasource.curation

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper

data class CurationScrapResponse(
    @SerializedName("isScraped")
    val isScraped: Boolean
) : DataToDomainMapper<Boolean> {
    override fun toDomainModel(): Boolean = isScraped
}