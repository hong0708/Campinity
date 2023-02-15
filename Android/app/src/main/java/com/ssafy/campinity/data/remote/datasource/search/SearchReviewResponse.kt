package com.ssafy.campinity.data.remote.datasource.search

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.search.Review

data class SearchReviewResponse(
    @SerializedName("reviewId")
    val reviewId: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("authorName")
    val authorName: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("memberId")
    val memberId: String
) : DataToDomainMapper<Review> {
    override fun toDomainModel(): Review = Review(
        reviewId = reviewId,
        content = content,
        createAt = createdAt,
        rate = rate,
        authorName = authorName,
        profileImage = profileImage,
        memberId = memberId
    )
}