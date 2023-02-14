package com.ssafy.campinity.core.entity.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.id.UUIDGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document(collection = "chat_rooms")
@Getter
@Setter
@ToString
public class ChatRoom {
    @Id
    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private String id;
    private String name;
    private List<String> users;
    private String fcmMessageBody;
    private LocalDate createdAt;

    @Builder
    public ChatRoom(UUID id, String name, List<String> users, String fcmMessageBody, LocalDate createdAt) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.users = users;
        this.fcmMessageBody = fcmMessageBody;
        this.createdAt = createdAt;
    }
}