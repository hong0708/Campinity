package com.ssafy.campinity.core.dto;


import com.ssafy.campinity.core.entity.question.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionResDTO {

    @ApiModelProperty(example = "질문 식별 아이디")
    private String questionId;

    @ApiModelProperty(example = "질문 내용")
    private String content;

    @ApiModelProperty(example = "질문 작성일")
    private String createdAt;

    @Builder
    public QuestionResDTO(Question question) {
        this.questionId = question.getUuid().toString();
        content = question.getContent();
        createdAt = String.valueOf(question.getCreatedAt());
    }
}
