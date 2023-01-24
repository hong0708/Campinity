package com.ssafy.campinity.core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResDTO {

    private final String email;
    private final String profileImg;
    private final String nickName;

    @Builder
    public MemberResDTO(String email, String profileImg, String nickName) {
        this.email = email;
        this.profileImg = profileImg;
        this.nickName = nickName;
    }
}
