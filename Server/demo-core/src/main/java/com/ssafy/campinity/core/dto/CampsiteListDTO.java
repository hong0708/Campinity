package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.campsite.CampsiteAndAmenity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteListDTO {
    // 이미지들이랑 쪽지 개수는 추후에 엔티티 완성되면 추가
    private int id;

    private UUID uuid;

    private int contentId;

    private String campName;

    private String address;

    private Double latitude;

    private Double longitude;

    @Builder
    public CampsiteListDTO(int id, UUID uuid, int contentId, String campName, String address, Double latitude, Double longitude) {
        this.id = id;
        this.uuid = uuid;
        this.contentId = contentId;
        this.campName = campName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
