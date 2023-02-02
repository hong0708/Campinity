package com.ssafy.campinity.api.dto.res;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CampsiteMetaDataDTO {
    String campsiteId;
    String campsiteName;
    Double lat;
    Double lng;

    @Builder
    public CampsiteMetaDataDTO(UUID campsiteId, String campsiteName, Double lat, Double lng) {
        this.campsiteId = campsiteId.toString();
        this.campsiteName = campsiteName;
        this.lat = lat;
        this.lng = lng;
    }
}
