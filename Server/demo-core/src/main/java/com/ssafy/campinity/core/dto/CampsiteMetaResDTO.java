package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteMetaResDTO {

    private String campsiteId;

    private String campName;

    private String doName;

    private String sigunguName;

    private String firstImageUrl;

    private Boolean isScraped;

    @Builder
    public CampsiteMetaResDTO(Campsite campsite, Boolean isScraped) {
        this.campsiteId = campsite.getUuid().toString();
        this.campName = campsite.getCampName();
        this.firstImageUrl = campsite.getFirstImageUrl();
        this.doName = campsite.getDoName();
        this.sigunguName = campsite.getSigunguName();
        this.isScraped = isScraped;
    }

    public CampsiteMetaResDTO(String campsiteId, String campName, String doName, String sigunguName, String firstImageUrl, Boolean isScraped) {
        this.campsiteId = campsiteId;
        this.campName = campName;
        this.doName = doName;
        this.sigunguName = sigunguName;
        this.firstImageUrl = firstImageUrl;
        this.isScraped = isScraped;
    }
}
