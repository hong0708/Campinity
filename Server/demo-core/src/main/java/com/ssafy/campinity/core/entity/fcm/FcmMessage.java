package com.ssafy.campinity.core.entity.fcm;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class FcmMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private UUID uuid;

    private String campsiteUuid;

    private String title;

    private String body;

    private String hiddenBody;

    private Double longitude;

    private Double latitude;

    @OneToOne
    @ToString.Exclude
    private Member member;

    private String appointeeToken;

    @Builder
    public FcmMessage(UUID uuid, String title, String body, String hiddenBody, Double longitude, Double latitude, Member member, String appointeeToken) {
        this.uuid = uuid;
        this.title = title;
        this.body = body;
        this.hiddenBody = hiddenBody;
        this.longitude = longitude;
        this.latitude = latitude;
        this.member = member;
        this.appointeeToken = appointeeToken;
    }



    public void appointReciever(String fcmToken){
        this.appointeeToken = fcmToken;
    }
}
