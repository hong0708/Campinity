package com.ssafy.campinity.api.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDeleteDTO {

    private String messageId;

    @Builder
    public MessageDeleteDTO(String messageId) {
        this.messageId = messageId;
    }
}
