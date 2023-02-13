package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper

data class SearchScrapResponse(
    @SerializedName("isScraped")
    val isScraped: Boolean
) : DataToDomainMapper<Boolean> {
    override fun toDomainModel(): Boolean = isScraped
}
