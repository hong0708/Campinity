package com.ssafy.campinity.core.dto;


import com.ssafy.campinity.core.entity.review.Review;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewResDTO {
    @ApiModelProperty(example = "리뷰 식별 아이디")
    private String reviewId;

    @ApiModelProperty(example = "리뷰 내용")
    private String content;

    @ApiModelProperty(value = "리뷰 생성 시간")
    private String createdAt;

    @ApiModelProperty(example = "리뷰 점수(1..5)")
    private Integer rate;

    @ApiModelProperty(value = "리뷰 작성자 닉네임")
    private String authorName;

    @ApiModelProperty(value = "리뷰 작성자 프로필 이미지")
    private String profileImage;

    @Builder
    public ReviewResDTO(Review review) {
        this.reviewId = review.getUuid().toString();
        this.content = review.getContent();
        this.createdAt = String.valueOf(review.getCreatedAt());
        this.rate = review.getRate();
        this.authorName = review.getMember().getName();
        this.profileImage = review.getMember().getProfileImage();
    }
}
