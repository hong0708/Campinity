package com.ssafy.campinity.data.remote.datasource.chat

import com.ssafy.campinity.data.remote.service.ChatApiService
import javax.inject.Inject

class ChatRemoteDataSourceImpl @Inject constructor(
    private val chatApiService: ChatApiService
) : ChatRemoteDataSource {
    override suspend fun getRooms(): List<RoomResponse> = chatApiService.getRooms()

    override suspend fun getMessages(roomId: String): MessageResponse =
        chatApiService.getMessages(roomId)
}