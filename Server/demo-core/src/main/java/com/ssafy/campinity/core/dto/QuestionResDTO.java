package com.ssafy.campinity.core.dto;


import com.ssafy.campinity.core.entity.question.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionResDTO {
    private String questionId;

    private String content;

    private LocalDateTime createdAt;

    @Builder
    public QuestionResDTO(Question question) {
        this.questionId = question.getUuid().toString();
        content = question.getContent();
        createdAt = question.getCreatedAt();
    }
}
