package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.campsite.CampsiteScrap;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteListResDTO {
    // 이미지들이랑 쪽지 개수는 추후에 엔티티 완성되면 추가

    private String campsiteId;

    private String campName;

    private String address;

    private Double latitude;

    private Double longitude;

    private boolean isScraped;

    @Builder
    public CampsiteListResDTO(Campsite camp, Member member) {
        boolean isScraped = false;
        for (CampsiteScrap cs: camp.getScraps()) {
            if (cs.getMember().equals(member)){
                isScraped = true;
                break;
            }
        }
        this.campsiteId = camp.getUuid().toString();
        this.campName = camp.getCampName();
        this.address = camp.getAddress();
        this.latitude = camp.getLatitude();
        this.longitude = camp.getLongitude();
        this.isScraped = isScraped;
    }
}
