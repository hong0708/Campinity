package com.ssafy.campinity.core.entity.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Document(collection = "chat_messages")
@Getter
@Setter
@ToString
public class ChatMessage {
    @Id
    private String id;
    private String roomId;
    private String sender;
    private String message;
    private Date timestamp;
    @Builder
    public ChatMessage(String id, String roomId, String sender, String message, Date timestamp) {
        this.id = id;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }
}