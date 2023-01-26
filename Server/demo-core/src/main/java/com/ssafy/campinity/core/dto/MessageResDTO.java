package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.message.Message;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageResDTO {

    @ApiModelProperty(example = "쪽지 식별 아이디")
    private String messageId;

    @ApiModelProperty(example = "(주)디노담양힐링파크 지점")
    private String campsiteName;

    @ApiModelProperty(example = "유저 이름")
    private String authorName;

    @ApiModelProperty(example = "리뷰 / 자유")
    private String messageCategory;

    @ApiModelProperty(example = "정수 range(0, INFINITY)")
    private int countLikes;

    @ApiModelProperty(example = "true / false")
    private boolean likeCheck;

    @ApiModelProperty(example = "글 내용")
    private String content;


    @ApiModelProperty(example = "baseUrl을 제외한 이미지 경로")
    private String imagePath;

    @ApiModelProperty(example = "쪽지 위도")
    private Double latitude;

    @ApiModelProperty(example = "쪽지 경도")
    private Double longitude;

    @ApiModelProperty(example = "자유 쪽지 유효 날짜")
    private String etcValidDate;

    @ApiModelProperty(example = "쪽지 작성일")
    private String createdAt;

    @ApiModelProperty(example = "쪽지 수정일")
    private String updatedAt;

    @Builder
    public MessageResDTO(Message message, UUID memberUuid) {

        this.messageId = String.valueOf(message.getUuid());
        this.campsiteName = message.getCampsite().getCampName();
        this.authorName = message.getMember().getName();
        this.messageCategory = message.getMessageCategory().getParam();
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
