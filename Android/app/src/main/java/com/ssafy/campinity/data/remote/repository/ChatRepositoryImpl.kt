package com.ssafy.campinity.data.remote.repository

import com.ssafy.campinity.common.util.wrapToResource
import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.data.remote.datasource.chat.ChatRemoteDataSource
import com.ssafy.campinity.domain.entity.chat.ChatItem
import com.ssafy.campinity.domain.entity.chat.RoomItem
import com.ssafy.campinity.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatRemoteDataSource: ChatRemoteDataSource
) : ChatRepository {
    override suspend fun getRooms(): Resource<List<RoomItem>> =
        wrapToResource(Dispatchers.IO) {
            chatRemoteDataSource.getRooms().map { it.toDomainModel() }
        }

    override suspend fun getMessages(roomId: String): Resource<List<ChatItem>> =
        wrapToResource(Dispatchers.IO) {
            chatRemoteDataSource.getMessages(roomId).chatMessages.map { it.toDomainModel() }
        }
}