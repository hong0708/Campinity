package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmTopicResDto {

    private String messageId;
    private String error;

    @Builder
    public FcmTopicResDto(String messageId, String error) {
        this.messageId = messageId;
        this.error = error;
    }
}
