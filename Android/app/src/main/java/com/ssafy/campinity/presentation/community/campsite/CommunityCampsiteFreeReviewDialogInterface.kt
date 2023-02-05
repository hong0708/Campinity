package com.ssafy.campinity.presentation.community.campsite

import okhttp3.MultipartBody

interface CommunityCampsiteFreeReviewDialogInterface {
    fun createFreeReviewNote(
        messageCategory: String,
        file: MultipartBody.Part?,
        latitude: Double,
        content: String,
        longitude: Double,
    )
}