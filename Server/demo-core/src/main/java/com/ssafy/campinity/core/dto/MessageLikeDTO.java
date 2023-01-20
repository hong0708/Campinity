package com.ssafy.campinity.core.dto;


import lombok.Data;
import lombok.Builder;

@Data
public class MessageLikeDTO {

    private boolean likeCheck;

    @Builder
    public MessageLikeDTO(boolean likeCheck) {
        this.likeCheck = likeCheck;
    }

}
