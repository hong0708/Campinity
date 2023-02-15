package com.ssafy.campinity.data.remote.datasource.chat

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.chat.RoomItem

data class RoomResponse(
    @SerializedName("roomId")
    val roomId: String,
    @SerializedName("otherProfilePath")
    val otherProfilePath: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("createdAt")
    val createdAt: String
) : DataToDomainMapper<RoomItem> {
    override fun toDomainModel(): RoomItem =
        RoomItem(roomId, title, subject, otherProfilePath, createdAt)
}