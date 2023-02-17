package com.ssafy.campinity.demo.batch.dto.gocamp.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.class)
public class ResCampsiteDto {

    private int contentId;
    private String facltNm;
    private String firstImageUrl;
    private String addr1;
    private String doNm;
    private String sigunguNm;
    private String mapX; //경도
    private String mapY; // 위도
    private String tel;
    private String homepage;
    private String resveCl;
    private String intro;
    private String lineIntro;
    private String exprnProgrm;
    private String sbrsEtc;
    private String operDeCl;
    private String eqpmnLendCl; // 캠핑장 장비 대여 가능 목록
    private String animalCmgCl;

    // 추후 N: M 관계 필드
    private String glampInnerFclty;
    private String caravInnerFclty;
    private String induty;
    private String sbrsCl;
    private String themaEnvrnCl;
    private String operPdCl;

}
