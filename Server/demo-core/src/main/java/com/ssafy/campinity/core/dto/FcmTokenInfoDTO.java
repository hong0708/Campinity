package com.ssafy.campinity.core.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmTokenInfoDTO {
    private String token;
    private String subscribeCampId;
    @Builder
    public FcmTokenInfoDTO(String token, String subscribeCampId) {
        this.token = token;
        this.subscribeCampId = subscribeCampId;
    }
}
