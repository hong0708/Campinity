package com.ssafy.campinity.domain.entity.chat

data class ChatItem(
    val roomId: String?,
    val sender: String?,
    val message: String?,
    val time: String?
)