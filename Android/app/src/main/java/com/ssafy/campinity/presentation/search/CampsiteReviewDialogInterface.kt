package com.ssafy.campinity.presentation.search

interface CampsiteReviewDialogInterface {
    fun postReview(campsiteId: String, content: String, rate: Int)
}