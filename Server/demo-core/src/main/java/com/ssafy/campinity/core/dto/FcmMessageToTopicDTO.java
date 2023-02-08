package com.ssafy.campinity.core.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
@Getter
public class FcmMessageToTopicDTO {

    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {

        private String topic; // 특정 그룹에 알림을 보내기위해 사용
        private Notification notification; // 모든 mobile os를 아우를수 있는 Notification
        private FcmData data;
        private Android android;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class FcmData {
        private String campsiteGroupId; // 캠핑장 그룹 redis 아이디
        private String fcmMessageId; // fcm Message Uuid
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Android {
        private String priority = "high";
        private String ttl = "60";
        private AndroidNotification notification;

    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class AndroidNotification {
        @JsonProperty("click_action")
        private String clickAction = "android_indent_filter"; // 클릭 시 연결할 android 인텐트 필터

    }
}
