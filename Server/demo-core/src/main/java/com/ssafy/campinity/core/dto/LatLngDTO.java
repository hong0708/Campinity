package com.ssafy.campinity.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class LatLngDTO {

    @ApiModelProperty(
            value = "좌상단 위도",
            required = true,
            dataType = "Double"
    )
    private double topLeftLat;

    @ApiModelProperty(
            value = "좌상단 경도",
            required = true,
            dataType = "Double"
    )
    private  double topLeftLng;

    @ApiModelProperty(
            value = "우하단 위도",
            required = true,
            dataType = "Double"
    )
    private  double bottomRightLat;

    @ApiModelProperty(
            value = "우하단 경도",
            required = true,
            dataType = "Double"
    )
    private double bottomRightLng;

    @Builder
    public LatLngDTO(String topLeftLat, String topLeftLng, String bottomRightLat, String bottomRightLng) {
        this.topLeftLat = Double.parseDouble(topLeftLat);
        this.topLeftLng = Double.parseDouble(topLeftLng);
        this.bottomRightLat = Double.parseDouble(bottomRightLat);
        this.bottomRightLng = Double.parseDouble(bottomRightLng);
    }
}
