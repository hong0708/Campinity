package com.ssafy.campinity.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialUserInfoDto {
    private Long id;
    private String nickname;
    private String email;

    public SocialUserInfoDto(String email) {
        this.email = email;
    }
}