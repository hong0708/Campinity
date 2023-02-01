package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteSearchResDTO {

    private String campsiteId;

    private String campName;

    private String address;

    @Builder
    public CampsiteSearchResDTO(Campsite campsite) {
        this.campsiteId = campsite.getUuid().toString();
        this.campName = campsite.getCampName();
        this.address = campsite.getAddress();
    }
}
