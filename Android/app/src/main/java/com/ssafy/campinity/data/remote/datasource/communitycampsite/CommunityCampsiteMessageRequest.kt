package com.ssafy.campinity.data.remote.datasource.communitycampsite

import okhttp3.MultipartBody

data class CommunityCampsiteMessageRequest(
    val messageCategory: String,
    val file: MultipartBody.Part?,
    val latitude:Double,
    val campsiteId: String,
    val content: String,
    val longitude:Double,
)
