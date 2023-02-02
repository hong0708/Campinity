package com.ssafy.campinity.api.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionDeleteDTO {

    private String questionId;
    @Builder
    public QuestionDeleteDTO(String questionId) {
        this.questionId = questionId;
    }
}
