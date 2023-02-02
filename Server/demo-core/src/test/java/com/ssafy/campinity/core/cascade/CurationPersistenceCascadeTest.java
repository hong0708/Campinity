package com.ssafy.campinity.core.cascade;

import com.ssafy.campinity.core.entity.curation.Curation;
import com.ssafy.campinity.core.entity.curation.CurationImage;
import com.ssafy.campinity.core.entity.curation.CurationScrap;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.curation.CurationImageRepository;
import com.ssafy.campinity.core.repository.curation.CurationScrapRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class CurationPersistenceCascadeTest {

    @Autowired
    CurationScrapRepository curationScrapRepository;

    @Autowired
    CurationImageRepository curationImageRepository;

    @Test
    @DisplayName("curation image persist 영속성 전이 테스트")
    public void curationImagePersistTest(){

        Curation curation = Curation.builder().curationCategory("전체").title("title").content("content").uuid(UUID.randomUUID())
                .firstImagePath("").build();

        CurationImage curationImage1 = CurationImage.builder().imagePath("").curation(curation).build();
        CurationImage curationImage2 = CurationImage.builder().imagePath("").curation(curation).build();

        CurationImage savedImages1 = curationImageRepository.save(curationImage1);
        CurationImage savedImages2 = curationImageRepository.save(curationImage2);

        Assertions.assertThat(savedImages1.getCuration().getId()).isEqualTo(curation.getId());
        Assertions.assertThat(savedImages2.getCuration().getId()).isEqualTo(curation.getId());
    }

    @Test
    @DisplayName("curation scrap persist 영속성 전이 테스트")
    public void curationScrapPersistTest (){

        Curation curation = Curation.builder().curationCategory("전체").title("title").content("content").uuid(UUID.randomUUID())
                .firstImagePath("").build();

        Member member = Member.builder().email("test@naver.com").name("test").profileImage("").
                uuid(UUID.randomUUID()).build();

        CurationScrap curationScrap = CurationScrap.builder().member(member).curation(curation).build();

        CurationScrap savedScrap = curationScrapRepository.save(curationScrap);

        Assertions.assertThat(savedScrap.getCuration().getId()).isEqualTo(curation.getId());
        Assertions.assertThat(savedScrap.getMember().getId()).isEqualTo(member.getId());
    }

}
