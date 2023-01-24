package com.ssafy.campinity.core.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyCollectionResDTO {

    private String collectionId;

    private String imagePath;

    @JsonSerialize()
    private String date;

    private String campsiteName;

    private String content;

    private String createdAt;

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
