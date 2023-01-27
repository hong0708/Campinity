package com.ssafy.campinity.data.remote.datasource.user

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("profileImg")
    val profileImg: String
)