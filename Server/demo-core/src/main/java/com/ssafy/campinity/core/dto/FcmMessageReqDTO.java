package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmMessageReqDTO {


    private String campsiteId;
    private String title;
    private String body;
    private String hiddenBody;
    private Double longitude;
    private Double latitude;


    @Builder

    public FcmMessageReqDTO(String campsiteId, String title, String body, String hiddenBody, Double longitude, Double latitude) {
        this.campsiteId = campsiteId;
        this.title = title;
        this.body = body;
        this.hiddenBody = hiddenBody;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
