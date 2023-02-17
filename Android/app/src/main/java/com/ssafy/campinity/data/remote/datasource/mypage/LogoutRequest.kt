package com.ssafy.campinity.data.remote.datasource.mypage

import com.google.gson.annotations.SerializedName

data class LogoutRequest(
    @SerializedName("atk")
    val atk: String,
    @SerializedName("fcmToken")
    val fcmToken: String,
    @SerializedName("rtk")
    val rtk: String
)
