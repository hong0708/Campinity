package com.ssafy.campinity.core.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessageToManyDTO {

    private boolean validate_only;
    @JsonProperty("multicast_message")
    private MulticastMessage multicastMessage;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class MulticastMessage {
        private List<String> tokens; // 특정 그룹에 알림을 보내기위해 사용
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
        private String redisId; // 캠핑장 그룹 redis 아이디
//        private String fcmMessageId; // fcm Message Uuid
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Android {
        private String priority = "high";
        private String ttl = "60";
//        private AndroidNotification notification;

    }
}
