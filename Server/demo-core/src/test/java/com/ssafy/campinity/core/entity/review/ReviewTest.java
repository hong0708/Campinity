package com.ssafy.campinity.core.entity.review;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.user.User;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.review.ReviewRepository;
import com.ssafy.campinity.core.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class ReviewTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("review 글 작성 및 삭제 테스트")
    public void reviewTest (){
        Campsite campsite = campsiteRepository.findById(1).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findById(1).orElseThrow(IllegalArgumentException::new);
        Review review = Review.builder().uuid(UUID.randomUUID()).rate(5).
                content("test용 게시글입니다. ").member(member).campsite(campsite).build();

        Review savedReview = reviewRepository.save(review);
        Assertions.assertThat(savedReview.getIsDeleted()).isEqualTo(false);

        reviewRepository.delete(savedReview);

        Review deletedReview = reviewRepository.findByUuid(savedReview.getUuid()).orElseThrow(IllegalArgumentException::new);

        Assertions.assertThat(deletedReview.getIsDeleted()).isEqualTo(true);
    }

}
