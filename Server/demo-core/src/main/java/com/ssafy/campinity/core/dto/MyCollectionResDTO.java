package com.ssafy.campinity.core.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyCollectionResDTO {
    @ApiModelProperty(example = "컬렉션 식별 아이디")
    private String collectionId;

    @ApiModelProperty(example = "이미지 경로")
    private String imagePath;

    @ApiModelProperty(example = "여행 날짜")
    private String date;

    @ApiModelProperty(example = "캠핑장 이름")
    private String campsiteName;

    @ApiModelProperty(example = "컬렉션 글 내용")
    private String content;

    @ApiModelProperty(example = "작성일")
    private String createdAt;

    @ApiModelProperty(example = "수정일")
    private String updatedAt;

    @Builder
    public MyCollectionResDTO(MyCollection myCollection) {
        this.collectionId = myCollection.getUuid().toString();
        this.imagePath = myCollection.getImagePath();
        this.date = myCollection.getDate();
        this.campsiteName = myCollection.getCampsiteName();
        this.content = myCollection.getContent();
        this.createdAt = myCollection.getCreatedAt().toString();
        this.updatedAt = myCollection.getUpdatedAt().toString();
    }
}
