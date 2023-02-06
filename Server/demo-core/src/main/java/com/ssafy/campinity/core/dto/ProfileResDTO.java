package com.ssafy.campinity.core.dto;

import com.ssafy.campinity.core.entity.fcm.FcmToken;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ProfileResDTO {

    private String name;
    private String imagePath;
    private List<FcmTokenInfoDTO> fcmTokenList;

    @Builder
    public ProfileResDTO(Member member) {
        this.name = member.getName();
        this.imagePath = member.getProfileImage();
        this.fcmTokenList = member.getFcmTokenList().stream().map(t ->
                FcmTokenInfoDTO.builder()
                        .token(t.getToken())
                        .subscribeCampId(t.getCampsiteUuid()).build())
                .collect(Collectors.toList());
    }
}
