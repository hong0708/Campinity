package com.ssafy.campinity.domain.entity.search

data class Review(
    val reviewId: String,
    val content: String,
    val createAt: String,
    val memberId: String,
    val rate: Int,
    val authorName: String,
    val profileImage: String
)