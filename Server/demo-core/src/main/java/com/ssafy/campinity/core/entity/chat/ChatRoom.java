package com.ssafy.campinity.core.entity.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "chat_rooms")
@Getter
@Setter
@ToString
public class ChatRoom {
    @Id
    private String id;
    private String name;
    private List<String> users;
    private String fcmMessageBody;

    @Builder
    public ChatRoom(String id, String name, List<String> users, String fcmMessageBody) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.fcmMessageBody = fcmMessageBody;
    }
}