package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteHighestRankingReqDTO {

    private String campsiteId;

    private String firstImageUrl;

    private String campName;

    private String doName;

    private String sigunguName;

    private double totalRate;

    @Builder
    public CampsiteHighestRankingReqDTO(String campsiteId, String firstImageUrl, String campName, String doName,
                                        String sigunguName, BigDecimal totalRate) {
        this.campsiteId = campsiteId;
        this.firstImageUrl = firstImageUrl;
        this.campName = campName;
        this.doName = doName;
        this.sigunguName = sigunguName;
        this.totalRate = totalRate.doubleValue();
    }
}
