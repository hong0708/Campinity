package com.ssafy.campinity.api.dto.res;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CampsiteMetaDataDTO {
    String campsiteId;
    String campsiteName;
    String address;
    @Builder
    public CampsiteMetaDataDTO(UUID campsiteId, String campsiteName, String address) {
        this.campsiteId = campsiteId.toString();
        this.campsiteName = campsiteName;
        this.address = address;
    }
}
