package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.answer.Answer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerResDTO {

    @ApiModelProperty(example = "답변 식별 아이디")
    private String answerId;

    @ApiModelProperty(example = "답변 내용")
    private String content;

    @ApiModelProperty(example = "답변 생성 시간")
    private String createdAt;

    @Builder
    public AnswerResDTO(Answer answer) {
        this.answerId = answer.getUuid().toString();
        this.content = answer.getContent();
        this.createdAt = String.valueOf(answer.getCreatedAt());
    }
}
