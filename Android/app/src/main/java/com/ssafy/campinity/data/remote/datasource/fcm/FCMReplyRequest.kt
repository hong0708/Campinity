package com.ssafy.campinity.data.remote.datasource.fcm

import com.google.gson.annotations.SerializedName

data class FCMReplyRequest(
    @SerializedName("fcmMessageId")
    val fcmMessageId: String,
    @SerializedName("fcmToken")
    val fcmToken: String
)