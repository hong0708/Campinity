package com.ssafy.campinity.data.remote.datasource.fcm

import com.google.gson.annotations.SerializedName

data class FCMTokenRequest(
    @SerializedName("campsiteId")
    val campsiteId: String,
    @SerializedName("fcmToken")
    val fcmToken: String
)