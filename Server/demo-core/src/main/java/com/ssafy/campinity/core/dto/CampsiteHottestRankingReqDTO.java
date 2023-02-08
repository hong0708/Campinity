package com.ssafy.campinity.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CampsiteHottestRankingReqDTO {

    private String campsiteId;

    private String firstImageUrl;

    private String campName;

    private String doName;

    private String sigunguName;

    private int questionCnt;

    private int answerCnt;

    private int fcmTokenCnt;

    private int messageCnt;

    @Builder
    public CampsiteHottestRankingReqDTO(String campsiteId, String firstImageUrl, String campName, String doName,
                                        String sigunguName, BigInteger questionCnt, BigInteger answerCnt, BigInteger messageCnt, BigInteger fcmTokenCnt) {
        this.campsiteId = campsiteId;
        this.firstImageUrl = firstImageUrl;
        this.campName = campName;
        this.doName = doName;
        this.sigunguName = sigunguName;
        this.questionCnt = questionCnt.intValue();
        this.answerCnt = answerCnt.intValue();
        this.messageCnt = messageCnt.intValue();
        this.fcmTokenCnt = fcmTokenCnt.intValue();
    }
}
