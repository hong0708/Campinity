package com.ssafy.campinity.domain.entity.chat

data class RoomItem(
    val roomId: String,
    val title: String,
    val subject: String,
    val otherProfileImg: String,
    val date: String
)
