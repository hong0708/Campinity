package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.campsite.CampsiteScrapRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteListResDTO {
    // 이미지들이랑 쪽지 개수는 추후에 엔티티 완성되면 추가
    @Autowired
    private static CampsiteScrapRepository campsiteScrapRepository;

    private String campsiteId;

    private String campName;

    private String address;

    private Double latitude;

    private Double longitude;

    private Boolean isScraped;

    @Builder
    public CampsiteListResDTO(Campsite camp, Boolean isScraped) {
        this.isScraped = isScraped;
        this.campsiteId = camp.getUuid().toString();
        this.campName = camp.getCampName();
        this.address = camp.getAddress();
        this.latitude = camp.getLatitude();
        this.longitude = camp.getLongitude();
    }
}
