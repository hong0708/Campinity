package com.ssafy.campinity.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class SocialUserInfoDto {
    private UUID uuid;
    private String nickname;
    private String email;
    private String profileImg;

    public SocialUserInfoDto(String email) {
        this.email = email;
    }
}