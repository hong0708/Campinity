package com.ssafy.campinity.api.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CampsiteMetaDataDTO {
    String campsiteId;
    String campsiteName;
    String address;
    String longitude;
    String latitude;
    @Builder
    public CampsiteMetaDataDTO(UUID campsiteId, String campsiteName, String address, String longitude, String latitude) {
        this.campsiteId = campsiteId.toString();
        this.campsiteName = campsiteName;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
