package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper

data class SearchDeleteReviewResponse(
    @SerializedName("reviewId")
    val reviewId: String
) : DataToDomainMapper<String> {
    override fun toDomainModel(): String = reviewId
}
