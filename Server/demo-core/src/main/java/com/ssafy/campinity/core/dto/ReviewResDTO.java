package com.ssafy.campinity.core.dto;


import com.ssafy.campinity.core.entity.review.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewResDTO {
    private String reviewId;

    private String content;

    private LocalDateTime createdAt;

    private int rate;

    private String authorName;

    private String profileImage;

    @Builder
    public ReviewResDTO(Review review) {
        this.reviewId = review.getUuid().toString();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.rate = review.getRate();
        this.authorName = review.getMember().getName();
        this.profileImage = review.getMember().getProfileImage();
    }
}
