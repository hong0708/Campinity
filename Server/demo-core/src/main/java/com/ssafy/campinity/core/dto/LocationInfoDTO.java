package com.ssafy.campinity.core.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Builder
public class LocationInfoDTO {
    double topLeftLat;
    double topLeftLng;
    double bottomRightLat;
    double bottomRightLng;
}
