package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName

data class SearchReviewRequest(
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("rate")
    val rate: Int
)
