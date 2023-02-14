package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClusteringDoLevelResDTO {

    private String doName;
    private double longitude;
    private double latitude;
    private int cnt;

    @Builder
    public ClusteringDoLevelResDTO(String doName, double longitude, double latitude, int cnt) {
        this.doName = doName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cnt = cnt;
    }
}
