package com.ssafy.campinity.data.remote.datasource.fcm

import com.google.gson.annotations.SerializedName

data class FCMMessageRequest(
    @SerializedName("body")
    val body: String,
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("hiddenBody")
    val hiddenBody: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("title")
    val title: String,
)