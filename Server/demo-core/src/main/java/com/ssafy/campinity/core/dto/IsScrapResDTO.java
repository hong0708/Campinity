package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsScrapResDTO {
    private Boolean isScraped;

    @Builder
    public IsScrapResDTO(Boolean isScraped) {
        this.isScraped = isScraped;
    }
}
