package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.message.MessageCategory;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)public class MessageReqDTO {

    private UUID campsiteId;

    private MessageCategory messageCategory;

    private String content;

    private double latitude;

    private double longitude;

    private MultipartFile file;


    @Builder
    public MessageReqDTO(String campsiteId, MessageCategory messageCategory, String content, double latitude, double longitude, MultipartFile file) {
        this.campsiteId = UUID.fromString(campsiteId);
        this.messageCategory = messageCategory;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.file = file;
    }

}
