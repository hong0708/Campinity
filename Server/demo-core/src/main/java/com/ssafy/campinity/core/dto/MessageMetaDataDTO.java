package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.message.Message;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageMetaDataDTO {

    @ApiModelProperty(example = "쪽지 식별 아이디")
    private String messageId;

    @ApiModelProperty(example = "(주)디노담양힐링파크 지점")
    private String campsiteName;

    @ApiModelProperty(example = "캠핑장 식별 아이디")
    private String campsiteId;

    @ApiModelProperty(example = "리뷰 / 자유")
    private String messageCategory;

    @ApiModelProperty(example = "글 내용")
    private String content;

    @ApiModelProperty(example = "쪽지 위도")
    private Double latitude;

    @ApiModelProperty(example = "쪽지 경도")
    private Double longitude;

    @Builder
    public MessageMetaDataDTO(Message message) {
        this.messageId = String.valueOf(message.getUuid());
        this.campsiteName = message.getCampsite().getCampName();
        this.campsiteId = message.getCampsite().getUuid().toString();
        this.messageCategory = message.getMessageCategory().getParam();
        this.content =  message.getContent();
        this.longitude = message.getLongitude();
        this.latitude = message.getLatitude();
    }
}
