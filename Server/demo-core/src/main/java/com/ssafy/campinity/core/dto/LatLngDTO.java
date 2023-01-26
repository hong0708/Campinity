package com.ssafy.campinity.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class LatLngDTO {

    @ApiModelProperty(
            value = "좌상단 위도",
            required = true,
            dataType = "Double",
            example = "37.1234"
    )
    private Double topLeftLat;

    @ApiModelProperty(
            value = "좌상단 경도",
            required = true,
            dataType = "Double",
            example = "128.23245"
    )
    private Double topLeftLng;

    @ApiModelProperty(
            value = "우하단 위도",
            required = true,
            dataType = "Double",
            example = "37.5678"
    )
    private Double bottomRightLat;

    @ApiModelProperty(
            value = "우하단 경도",
            required = true,
            dataType = "Double",
            example = "128.3457"
    )
    private Double bottomRightLng;

    @Builder
    public LatLngDTO(String topLeftLat, String topLeftLng, String bottomRightLat, String bottomRightLng) {
        this.topLeftLat = Double.parseDouble(topLeftLat);
        this.topLeftLng = Double.parseDouble(topLeftLng);
        this.bottomRightLat = Double.parseDouble(bottomRightLat);
        this.bottomRightLng = Double.parseDouble(bottomRightLng);
    }
}
