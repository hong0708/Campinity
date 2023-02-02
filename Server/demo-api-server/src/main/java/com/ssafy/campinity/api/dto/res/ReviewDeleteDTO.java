package com.ssafy.campinity.api.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDeleteDTO {

    private String reviewId;
    @Builder
    public ReviewDeleteDTO(String reviewId) {
        this.reviewId = reviewId;
    }
}
