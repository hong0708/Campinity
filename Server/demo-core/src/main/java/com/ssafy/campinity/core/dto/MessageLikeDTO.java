package com.ssafy.campinity.core.dto;


import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageLikeDTO {

    private boolean likeCheck;

    @Builder
    public MessageLikeDTO(boolean likeCheck) {
        this.likeCheck = likeCheck;
    }

}
