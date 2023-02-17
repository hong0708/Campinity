package com.ssafy.campinity.core.entity.review;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.review.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
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
        Campsite campsite = Campsite.builder().doName("캠핑군").uuid(UUID.randomUUID()).messages(new ArrayList<>()).build();
        Campsite savedCampsite = campsiteRepository.save(campsite);

        Member member = Member.builder().uuid(UUID.randomUUID()).name("test").build();
        Member savedMember = memberRepository.save(member);

        Review review = Review.builder().uuid(UUID.randomUUID()).rate(5).
                content("test용 게시글입니다. ").member(member).campsite(savedCampsite).build();

        Review savedReview = reviewRepository.save(review);
        Assertions.assertThat(savedReview.getExpired()).isEqualTo(false);

        reviewRepository.delete(savedReview);

        Review deletedReview = reviewRepository.findByUuid(savedReview.getUuid()).orElseThrow(IllegalArgumentException::new);

        Assertions.assertThat(deletedReview.getExpired()).isEqualTo(true);
    }

}
