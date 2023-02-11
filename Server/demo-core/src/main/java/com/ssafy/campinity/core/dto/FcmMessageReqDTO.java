package com.ssafy.campinity.core.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmMessageReqDTO {


    @ApiModelProperty(
            value = "현재 유저가 존재하는 캠핑장 id",
            required = true,
            dataType = "String"
    )
    private String campsiteId;
    @ApiModelProperty(
            value = "도움 주기/ 도움 받기(띄어쓰기까지 일치해야 합니다.)",
            required = true,
            dataType = "String"
    )
    private String title;
    @ApiModelProperty(
            value = "도움 주기/ 도움 받기(띄어쓰기까지 일치해야 합니다.)",
            required = true,
            dataType = "String"
    )
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
