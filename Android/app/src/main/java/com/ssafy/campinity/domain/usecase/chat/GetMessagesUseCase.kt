package com.ssafy.campinity.domain.usecase.chat

import com.ssafy.campinity.data.remote.Resource
import com.ssafy.campinity.domain.entity.chat.ChatItem
import com.ssafy.campinity.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(roomId: String): Resource<List<ChatItem>> =
        withContext(Dispatchers.IO) {
            chatRepository.getMessages(roomId)
        }
}