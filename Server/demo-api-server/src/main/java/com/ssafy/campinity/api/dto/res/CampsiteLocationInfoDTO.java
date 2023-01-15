package com.ssafy.campinity.api.dto.res;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CampsiteLocationInfoDTO {
    UUID campsiteId;
    Double lat;
    Double lng;
}
