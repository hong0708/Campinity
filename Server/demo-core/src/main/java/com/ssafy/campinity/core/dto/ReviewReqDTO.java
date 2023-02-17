package com.ssafy.campinity.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReqDTO {
    @ApiModelProperty(
            value = "캠핑장 식별 아이디",
            required = true,
            dataType = "String"
    )
    private UUID campsiteId;

    @ApiModelProperty(
            value = "리뷰 내용",
            required = true,
            dataType = "String"
    )
    private String content;

    @ApiModelProperty(
            value = "리뷰 점수",
            required = true,
            dataType = "Integer"
    )
    private Integer rate;

    @Builder
    public ReviewReqDTO(UUID campsiteId, String content, int rate) {
        this.campsiteId = campsiteId;
        this.content = content;
        this.rate = rate;
    }
}
