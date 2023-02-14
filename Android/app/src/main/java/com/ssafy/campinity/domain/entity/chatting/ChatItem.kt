package com.ssafy.campinity.domain.entity.chatting

data class ChatItem(
    val roomId: String,
    val name: String,
    val ProfileImg: String?,
    val message: String,
    val time: String
)