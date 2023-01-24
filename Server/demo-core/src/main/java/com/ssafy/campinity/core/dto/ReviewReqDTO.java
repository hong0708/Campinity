package com.ssafy.campinity.core.dto;

import lombok.*;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReqDTO {
    UUID campsiteId;

    UUID memberId;

    String content;

    int rate;

    @Builder
    public ReviewReqDTO(UUID campsiteId, UUID memberId, String content, int rate) {
        this.campsiteId = campsiteId;
        this.memberId = memberId;
        this.content = content;
        this.rate = rate;
    }
}
