package com.ssafy.campinity.domain.entity.chat

data class ChatItem(
    val roomId: String,
    val name: String,
    val ProfileImg: String?,
    val message: String,
    val time: String
)