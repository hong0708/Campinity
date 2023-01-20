package com.ssafy.campinity.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LatLngDTO {
    private double topLeftLat;
    private  double topLeftLng;
    private  double bottomRightLat;
    private double bottomRightLng;

    @Builder
    public LatLngDTO(String topLeftLat, String topLeftLng, String bottomRightLat, String bottomRightLng) {
        this.topLeftLat = Double.parseDouble(topLeftLat);
        this.topLeftLng = Double.parseDouble(topLeftLng);
        this.bottomRightLat = Double.parseDouble(bottomRightLat);
        this.bottomRightLng = Double.parseDouble(bottomRightLng);
    }
}
