package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteRankingResDTO {

    private int ranking;

    private String campsiteId;

    private String campName;

    private String doName;

    private String sigunguName;

    private String firstImageUrl;

    @Builder
    public CampsiteRankingResDTO(int ranking, String campsiteId, String campName, String doName, String sigunguName, String firstImageUrl) {
        this.ranking = ranking;
        this.campsiteId = campsiteId;
        this.campName = campName;
        this.doName = doName;
        this.sigunguName = sigunguName;
        this.firstImageUrl = firstImageUrl;
    }
}
