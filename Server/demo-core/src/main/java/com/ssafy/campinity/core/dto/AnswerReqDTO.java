package com.ssafy.campinity.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerReqDTO {

    @ApiModelProperty(
            value = "질문 식별 아이디",
            required = true,
            dataType = "String"
    )
    private UUID questionId;

    @ApiModelProperty(
            value = "멤버 식별 아이디",
            required = true,
            dataType = "String"
    )
    private UUID memberId;

    @ApiModelProperty(
            value = "답변 내용",
            required = true,
            dataType = "String"
    )
    private String content;

    @Builder
    public AnswerReqDTO(UUID questionId, UUID memberId, String content) {
        this.questionId = questionId;
        this.memberId = memberId;
        this.content = content;
    }
}
