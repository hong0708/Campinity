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
    @Autowired
    private static CampsiteScrapRepository campsiteScrapRepository;

    private String campsiteId;

    private String campName;

    private String address;

    private Double latitude;

    private Double longitude;

    private String firstImageUrl;

    private Boolean isScraped;

    private List<String> images;

    private int messageCnt;

    @Builder
    public CampsiteListResDTO(Campsite camp, Boolean isScraped, int messageCnt) {
        this.isScraped = isScraped;
        this.campsiteId = camp.getUuid().toString();
        this.campName = camp.getCampName();
        this.address = camp.getAddress();
        this.latitude = camp.getLatitude();
        this.longitude = camp.getLongitude();
        this.images = new ArrayList<>();
        this.messageCnt = messageCnt;
        this.firstImageUrl = camp.getFirstImageUrl();
    }
}
