package com.ssafy.campinity.domain.repository

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.chat.RoomItem

interface ChatRepository {

    suspend fun getRooms(): Resource<List<RoomItem>>
}