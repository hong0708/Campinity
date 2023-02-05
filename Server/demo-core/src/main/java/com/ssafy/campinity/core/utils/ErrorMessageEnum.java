package com.ssafy.campinity.core.utils;

import lombok.Getter;

@Getter
public enum ErrorMessageEnum {
    CAMPSITE_NOT_FOUND("캠핑장을 찾을 수 없습니다."),
    USER_NOT_EXIST("존재하지 않는 회원입니다"),
    MESSAGE_NOT_EXIST("이미 삭제 됐거나 존재하지 않은 메세지입니다."),
    FCMTOKEN_NOT_EXIST("FCM 토큰이 존재하지 않습니다.");

    private String message;

    ErrorMessageEnum(String message) {
        this.message = message;
    }
}
