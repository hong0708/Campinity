package com.ssafy.campinity.data.remote.datasource.collection

import okhttp3.MultipartBody

data class CollectionRequest(
    val campsiteName: String,
    val content: String,
    val date: String,
    val file: MultipartBody.Part?
)
