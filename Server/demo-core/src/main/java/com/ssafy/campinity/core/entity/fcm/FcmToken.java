package com.ssafy.campinity.core.entity.fcm;


import com.ssafy.campinity.core.entity.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


/*
* id : fcmToken 식별자
* member : fcmToken과 매칭되는 기기의 보유 유저
* fcmToken : 유저 fcmToken
* campsiteUuid : 유저가 알람받기 신청한 campsiteUuid(fcm에서 topic)
 */

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
@Entity
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Member member;

    private String token = "";

    private String campsiteUuid;

    private LocalDate expiredDate; // 생성날짜 기준 + 한 달

    @Builder
    public FcmToken(Member member, String token, String campsiteUuid, LocalDate expiredDate) {
        this.member = member;
        this.token = token;
        this.campsiteUuid = campsiteUuid;
        this.expiredDate = expiredDate;
    }

    public void refreshFcmToken(String token){
        this.token = token;
        this.expiredDate = LocalDate.now().plusMonths(1);
    }

    public void subscribeCamp(String campsiteUuid){
       this.campsiteUuid = campsiteUuid;
    }
}
