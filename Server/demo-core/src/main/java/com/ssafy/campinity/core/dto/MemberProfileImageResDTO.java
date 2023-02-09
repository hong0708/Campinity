package com.ssafy.campinity.core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberProfileImageResDTO {

    String profile;

    @Builder
    public MemberProfileImageResDTO(String profile) {
        this.profile = profile == null ? "" : profile;
    }
}
