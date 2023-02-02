package com.ssafy.campinity.data.remote.datasource.curation

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.curation.CurationDetailItem

data class CurationDetailResponse(
    @SerializedName("content")
    val content: String,
    @SerializedName("imagePaths")
    val imagePaths: List<String>,
    @SerializedName("isScraped")
    val isScraped: Boolean,
    @SerializedName("title")
    val title: String,
) : DataToDomainMapper<CurationDetailItem> {
    override fun toDomainModel(): CurationDetailItem =
        CurationDetailItem(content, imagePaths, isScraped, title)
}