package com.ssafy.campinity.core.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteScrapDTO {

    private boolean isScraped;

    @Builder
    public CampsiteScrapDTO(boolean isScraped) {
        this.isScraped = isScraped;
    }
}
