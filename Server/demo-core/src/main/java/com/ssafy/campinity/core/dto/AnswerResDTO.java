package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.answer.Answer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerResDTO {

    private String answerId;

    private String content;

    private LocalDateTime createdAt;

    @Builder
    public AnswerResDTO(Answer answer) {
        this.answerId = answer.getUuid().toString();
        this.content = answer.getContent();
        this.createdAt = answer.getCreatedAt();
    }
}
