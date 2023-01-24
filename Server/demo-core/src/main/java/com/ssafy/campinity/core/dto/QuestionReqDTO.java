package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionReqDTO {

    UUID memberId;

    UUID campsiteId;

    String content;

    @Builder
    public QuestionReqDTO(UUID memberId, UUID campsiteId, String content) {
        this.memberId = memberId;
        this.campsiteId = campsiteId;
        this.content = content;
    }
}
