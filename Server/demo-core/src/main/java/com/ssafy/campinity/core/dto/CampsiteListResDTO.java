package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.repository.campsite.CampsiteScrapRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteListResDTO {

    private String campsiteId;

    private String campName;

    private String doName;

    private String sigunguName;

    private Double latitude;

    private Double longitude;

    private Boolean isScraped;

    private List<String> images;

    private int messageCnt;

    @Builder
    public CampsiteListResDTO(Campsite camp, Boolean isScraped, int messageCnt, List<String> images) {
        this.isScraped = isScraped;
        this.campsiteId = camp.getUuid().toString();
        this.campName = camp.getCampName();
        this.latitude = camp.getLatitude();
        this.longitude = camp.getLongitude();
        this.images = images;
        this.messageCnt = messageCnt;
        this.doName = camp.getDoName();
        this.sigunguName = camp.getSigunguName();
    }
}
