package com.ssafy.campinity.core.entity.fcmToken;


import com.ssafy.campinity.core.entity.BaseEntity;
import com.ssafy.campinity.core.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/*
* id : fcmToken 식별자
* member : fcmToken과 매칭되는 기기의 보유 유저
* fcmToken : 유저 fcmToken
* campsiteUuid : 유저가 알람받기 신청한 campsiteUuid(fcm에서 topic)
 */

@Getter
@NoArgsConstructor
@Entity
public class FcmToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "fcmToken")
    private Member member;

    private String fcmToken;

    private String campsiteUuid;

    @Builder
    public FcmToken(Member member, String fcmToken, String campsiteUuid) {
        this.member = member;
        this.fcmToken = fcmToken;
        this.campsiteUuid = campsiteUuid;
    }

    public void refreshMemberFcmToken(String fcmToken){
       this.fcmToken = fcmToken;
    }

    public void subscribeCamp(String campsiteUuid){
       this.campsiteUuid = campsiteUuid;
    }
    public void unsubscribeCamp(){
       this.campsiteUuid = "";
    }
}
