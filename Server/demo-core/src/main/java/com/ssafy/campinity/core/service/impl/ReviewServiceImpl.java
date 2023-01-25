package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.dto.ReviewReqDTO;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.review.Review;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.review.ReviewRepository;
import com.ssafy.campinity.core.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
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
    public void createReview(ReviewReqDTO reviewReqDTO, int memberId) {
        Member member = memberRepository.findMemberByIdAndExpiredIsFalse(memberId)
                .orElseThrow(IllegalArgumentException::new);
        Campsite campsite = campsiteRepository.findByUuid(reviewReqDTO.getCampsiteId())
                .orElseThrow(IllegalArgumentException::new);

        Review review = Review.builder().uuid(UUID.randomUUID()).
                content(reviewReqDTO.getContent()).rate(reviewReqDTO.getRate()).campsite(campsite).
                member(member).build();

        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(UUID reviewId, UUID memberUuid) throws NoPermissionException {

        Review review = reviewRepository.findByUuid(reviewId).orElseThrow(IllegalArgumentException::new);

        if (!review.getMember().getUuid().equals(memberUuid))
            throw new NoPermissionException("삭제 권한이 없습니다.");

        reviewRepository.delete(review);
    }
}
