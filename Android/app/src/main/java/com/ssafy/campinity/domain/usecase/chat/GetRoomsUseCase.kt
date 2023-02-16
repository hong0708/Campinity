package com.ssafy.campinity.domain.usecase.chat

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.chat.RoomItem
import com.ssafy.campinity.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRoomsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): Resource<List<RoomItem>> =
        withContext(Dispatchers.IO) {
            chatRepository.getRooms()
        }
}