package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.answer.Answer;
import com.ssafy.campinity.core.entity.question.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionDetailResDTO {

    @ApiModelProperty(example = "질문 식별 아이디")
    private String questionId;

    @ApiModelProperty(example = "질문 내용")
    private String content;

    @ApiModelProperty(example = "질문 작성일")
    private String createdAt;

    @ApiModelProperty(example = "답글 목록")
    private List<AnswerResDTO> answers;


    @Builder
    public QuestionDetailResDTO(Question question, List<AnswerResDTO> answers) {
        this.questionId = question.getUuid().toString();
        this.content = question.getContent();
        this.createdAt = question.getCreatedAt().toString();
        this.answers = answers;
    }
}
