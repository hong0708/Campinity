package com.ssafy.campinity.data.remote.datasource.chat

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.chat.ChatItem
import java.time.LocalDateTime

data class ChatResponse(
    @SerializedName("roomId")
    val roomId: String?,
    @SerializedName("sender")
    val sender: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("timestamp")
    val timestamp: LocalDateTime?
) : DataToDomainMapper<ChatItem> {
    override fun toDomainModel(): ChatItem =
        ChatItem(roomId, sender, message, timestamp.toString())
}