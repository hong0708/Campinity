package com.ssafy.campinity.api.dto.notification;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampsiteListDTO {
    // 이미지들이랑 쪽지 개수는 추후에 엔티티 완성되면 추가
    private int campsiteId;

    private UUID uuid;


    private int contentId;

    private String campName;

    private String firstImageUrl;

    private String address;

    private Double latitude;

    private Double loggitude;

}
