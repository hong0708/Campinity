package com.ssafy.campinity.api.dto.res;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyCollectionDeleteDTO {

    private String collectionId;

    @Builder
    public MyCollectionDeleteDTO(String collectionId) {
        this.collectionId = collectionId;
    }
}
