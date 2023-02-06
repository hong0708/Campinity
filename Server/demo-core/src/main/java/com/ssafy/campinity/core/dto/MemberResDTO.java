package com.ssafy.campinity.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResDTO {

    @ApiModelProperty(example = "darkbyte1308@gmail.com")
    private final String email;

    @ApiModelProperty(example= "/images/member/24.jpg")

    private final String profileImg;

    @ApiModelProperty(example = "nickName")
    private final String nickName;

    @Builder
    public MemberResDTO(String email, String profileImg, String nickName) {
        this.email = email;
        this.profileImg = profileImg;
        this.nickName = nickName;
    }
}
