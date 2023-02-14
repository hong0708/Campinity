package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClusteringSigunguLevelResDTO {

    private String sigunguName;
    private double longitude;
    private double latitude;
    private int cnt;

    @Builder
    public ClusteringSigunguLevelResDTO(String sigunguName, double longitude, double latitude, int cnt) {
        this.sigunguName = sigunguName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cnt = cnt;
    }

}
