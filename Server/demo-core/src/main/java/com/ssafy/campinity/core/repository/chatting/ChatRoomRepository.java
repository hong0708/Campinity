package com.ssafy.campinity.core.repository.chatting;

import com.ssafy.campinity.core.entity.chat.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
}