package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteMetaResDTO {

    private String campsiteId;

    private String campName;

    private String address;

    private String firstImageUrl;

    @Builder
    public CampsiteMetaResDTO(UUID campsiteId, String campName, String address, String firstImageUrl) {
        this.campsiteId = campsiteId.toString();
        this.campName = campName;
        this.address = address;
        this.firstImageUrl = firstImageUrl;
    }
}
