package com.ssafy.campinity.data.remote.datasource.chat

import com.google.gson.annotations.SerializedName
import com.ssafy.campinity.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.campinity.domain.entity.chat.ChatItem

data class ChatResponse(
    @SerializedName("sender")
    val sender: String?,
    @SerializedName("context")
    val message: String?,
    @SerializedName("timeStamp")
    val timestamp: String?
) : DataToDomainMapper<ChatItem> {
    override fun toDomainModel(): ChatItem =
        ChatItem(sender, message, timestamp.toString())
}

data class MessageResponse(
    @SerializedName("chatMessages")
    val chatMessages: List<ChatResponse>
)