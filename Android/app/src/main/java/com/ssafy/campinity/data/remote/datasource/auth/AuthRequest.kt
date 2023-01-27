package com.ssafy.campinity.data.remote.datasource.auth

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("accessToken")
    val accessToken: String
)