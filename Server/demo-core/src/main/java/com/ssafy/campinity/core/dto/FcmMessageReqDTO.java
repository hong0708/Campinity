package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmMessageReqDTO {

    private String title;
    private String body;
    private String hiddenBody;

    @Builder
    public FcmMessageReqDTO(String title, String body, String hiddenBody) {
        this.title = title;
        this.body = body;
        this.hiddenBody = hiddenBody;
    }
}
