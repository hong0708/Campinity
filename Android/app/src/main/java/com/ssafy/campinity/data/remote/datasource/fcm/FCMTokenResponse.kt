package com.ssafy.campinity.data.remote.datasource.fcm

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.fcm.FCMToken

data class FCMTokenResponse(
    @SerializedName("expiredDate")
    val expiredDate: String,
    @SerializedName("subscribeCampId")
    val subscribeCampId: String,
    @SerializedName("token")
    val token: String
) : DataToDomainMapper<FCMToken> {
    override fun toDomainModel(): FCMToken =
        FCMToken(expiredDate, subscribeCampId, token)
}