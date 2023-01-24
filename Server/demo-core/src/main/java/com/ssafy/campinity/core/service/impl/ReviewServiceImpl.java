package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.dto.ReviewReqDTO;
import com.ssafy.campinity.core.dto.ReviewResDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.review.Review;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.review.ReviewRepository;
import com.ssafy.campinity.core.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public ReviewResDTO createReview(ReviewReqDTO reviewReqDTO, int requestMemberId) {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(requestMemberId)
                .orElseThrow(IllegalArgumentException::new);
        Campsite campsite = campsiteRepository.findByUuid(reviewReqDTO.getCampsiteId())
                .orElseThrow(IllegalArgumentException::new);

        Review review = Review.builder().uuid(UUID.randomUUID()).
                content(reviewReqDTO.getContent()).rate(reviewReqDTO.getRate()).campsite(campsite).
                member(member).build();

        Review savedReview = reviewRepository.save(review);

        return ReviewResDTO.builder().review(savedReview).build();
    }

    @Override
    public void deleteReview(UUID reviewId, UUID requestMemberId) throws Exception {
        Review review = reviewRepository.findByUuid(reviewId).orElseThrow(IllegalArgumentException::new);
        if (review.getMember().getUuid().equals(requestMemberId)) {
            reviewRepository.delete(review);
        } else {
            throw new Exception("삭제 권한이 없습니다.");
        }
    }
}
