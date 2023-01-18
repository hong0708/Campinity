package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteAndAmenity;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.user.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteListDTO {
    // 이미지들이랑 쪽지 개수는 추후에 엔티티 완성되면 추가

    private UUID campsiteId;

    private String campName;

    private String address;

    private Double latitude;

    private Double longitude;

    private boolean isScraped;

    @Builder
    public CampsiteListDTO(Campsite camp, User user) {
        boolean isScraped = false;
        for (CampsiteScrap cs: camp.getScraps()) {
            if (cs.getUser().equals(user)){
                isScraped = true;
                break;
            }
        }
        this.campsiteId = camp.getUuid();
        this.campName = camp.getCampName();
        this.address = camp.getAddress();
        this.latitude = camp.getLatitude();
        this.longitude = camp.getLongitude();
        this.isScraped = isScraped;
    }
}
