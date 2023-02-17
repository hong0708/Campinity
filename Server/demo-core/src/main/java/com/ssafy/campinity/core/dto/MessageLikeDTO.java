package com.ssafy.campinity.core.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageLikeDTO {

    @ApiModelProperty(
            example = "true"
    )
    private boolean likeCheck;

    @Builder
    public MessageLikeDTO(boolean likeCheck) {
        this.likeCheck = likeCheck;
    }

}
