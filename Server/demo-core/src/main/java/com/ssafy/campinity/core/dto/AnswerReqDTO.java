package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerReqDTO {

    private UUID questionId;

    private UUID memberId;

    private String content;

    @Builder
    public AnswerReqDTO(UUID questionId, UUID memberId, String content) {
        this.questionId = questionId;
        this.memberId = memberId;
        this.content = content;
    }
}
