package com.ssafy.campinity.core.dto;


import com.ssafy.campinity.core.entity.question.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionResDTO {
    @ApiModelProperty(
            value = "질문 식별 아이디",
            required = true,
            dataType = "String"
    )
    private String questionId;

    @ApiModelProperty(
            value = "질문 내용",
            required = true,
            dataType = "String"
    )
    private String content;

    @ApiModelProperty(
            value = "질문 생성 시간",
            required = true,
            dataType = "String"
    )
    private String createdAt;

    @Builder
    public QuestionResDTO(Question question) {
        this.questionId = question.getUuid().toString();
        content = question.getContent();
        createdAt = String.valueOf(question.getCreatedAt());
    }
}
