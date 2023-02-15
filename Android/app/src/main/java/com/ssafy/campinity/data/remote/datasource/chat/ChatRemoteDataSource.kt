package com.ssafy.campinity.data.remote.datasource.chat

interface ChatRemoteDataSource {

    suspend fun getRooms(): List<RoomResponse>

    suspend fun getMessages(roomId: String): MessageResponse
}