package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.message.Message;
import lombok.*;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageResDTO {

    private String messageId;

    private String campsiteName;

    private String authorName;

    private String messageCategory;

    private int countLikes;

    private boolean likeCheck;

    private String content;

    private String imagePath;

    private Double longitude;

    private Double latitude;

    private String etcValidDate;

    private String createdAt;

    private String updatedAt;

    @Builder
    public MessageResDTO(Message message, UUID memberUuid) {

        this.messageId = String.valueOf(message.getUuid());
        this.campsiteName = message.getCampsite().getCampName();
        this.authorName = message.getMember().getName();
        this.messageCategory = message.getMessageCategory().name();
        this.countLikes = message.getLikeMessages() == null ? 0 : message.getLikeMessages().size();
        this.content =  message.getContent();
        this.imagePath = message.getImagePath();
        this.longitude = message.getLongitude();
        this.latitude = message.getLatitude();
        this.likeCheck = message.getLikeMessages() != null &&
                message.getLikeMessages().stream().anyMatch(likeMessage ->
                likeMessage.getMember().getUuid().equals(memberUuid));
        this.etcValidDate = String.valueOf(message.getCreatedAt().plusDays(2));
        this.createdAt = String.valueOf(message.getCreatedAt());
        this.updatedAt = String.valueOf(message.getUpdatedAt());
    }
}
