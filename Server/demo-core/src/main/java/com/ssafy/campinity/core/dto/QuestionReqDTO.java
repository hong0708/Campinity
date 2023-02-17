package com.ssafy.campinity.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionReqDTO {

    @ApiModelProperty(
            value = "캠핑장 식별 아이디",
            required = true
    )
    UUID campsiteId;

    @ApiModelProperty(
            value = "질문 내용",
            required = true,
            dataType = "String"
    )
    String content;

    @Builder
    public QuestionReqDTO(UUID campsiteId, String content) {
        this.campsiteId = campsiteId;
        this.content = content;
    }
}
