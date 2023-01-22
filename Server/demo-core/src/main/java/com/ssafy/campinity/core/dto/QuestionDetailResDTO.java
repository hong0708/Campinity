package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.answer.Answer;
import com.ssafy.campinity.core.entity.question.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionDetailResDTO {
    private String questionId;

    private String content;

    private LocalDateTime createdAt;

    private List<AnswerResDTO> answers;


    @Builder
    public QuestionDetailResDTO(Question question, List<AnswerResDTO> answers) {
        this.questionId = question.getUuid().toString();
        this.content = question.getContent();
        this.createdAt = question.getCreatedAt();
        this.answers = answers;
    }
}
