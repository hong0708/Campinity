package com.ssafy.campinity.data.remote.datasource.curation

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.curation.CurationItem

data class CurationResponse(
    @SerializedName("curationId")
    val curationId: String,
    @SerializedName("firstImagePath")
    val firstImagePath: String,
    @SerializedName("title")
    val title: String
) : DataToDomainMapper<CurationItem> {
    override fun toDomainModel(): CurationItem =
        CurationItem(curationId, firstImagePath, title)
}