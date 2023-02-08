package com.ssafy.campinity.demo.batch.JobTest;


import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.message.MessageRepository;
import com.ssafy.campinity.demo.batch.config.BatchDataSourceConfig;
import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
import com.ssafy.campinity.demo.batch.config.TestBatchConfig;
import com.ssafy.campinity.demo.batch.job.SoftDeleteEtcMessageConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SoftDeleteEtcMessageConfig.class, TestBatchConfig.class,
        BatchDataSourceConfig.class, CampinityDataSourceConfig.class})
@SpringBatchTest
public class SoftDeleteTotalTest {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @DisplayName("soft delete job 통합 테스트")
    public void softDeleteJobTotalTest () throws Exception {
        Campsite campsite = Campsite.builder().uuid(UUID.randomUUID()).campName("test")
                .amenities(new ArrayList<>()).amenities(new ArrayList<>()).messages(new ArrayList<>())
                .caravanFclties(new ArrayList<>()).glampFclties(new ArrayList<>()).industries(new ArrayList<>())
                .openSeasons(new ArrayList<>()).themes(new ArrayList<>()).build();

        Member member = Member.builder().uuid(UUID.randomUUID()).email("test@email.com").build();

        Campsite savedCampsite = campsiteRepository.save(campsite);
        Member savedMember = memberRepository.save(member);

        LocalDateTime date = LocalDateTime.now().minusDays(3);

        Message message1 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(campsite)
                .member(savedMember).messageCategory("자유").build();
        Message savedMessage1 = messageRepository.save(message1);
        savedMessage1.setCreatedAt(date);
        Message m1 = messageRepository.save(savedMessage1);

        Message message2 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(campsite)
                .member(savedMember).messageCategory("자유").build();
        Message savedMessage2 = messageRepository.save(message2);
        savedMessage2.setCreatedAt(date);
        Message m2 = messageRepository.save(savedMessage2);

        Message message3 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(campsite)
                .member(savedMember).messageCategory("자유").build();
        message3.setCreatedAt(date);
        Message savedMessage3 = messageRepository.save(message3);

        Message message4 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(campsite)
                .member(savedMember).messageCategory("리뷰").build();
        Message savedMessage4 = messageRepository.save(message4);
        savedMessage4.setCreatedAt(date);
        Message m4 = messageRepository.save(savedMessage4);


        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", Long.toString(System.currentTimeMillis())).toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);  // job 실행 결과가 성공인지
        List<Message> messages = messageRepository.findByCampsite_id(savedCampsite.getId());
        Assertions.assertThat(messages.size()).isEqualTo(4);
        Assertions.assertThat(messages.get(0).getExpired()).isEqualTo(true); // soft delete가 정상적으로 실행되었는지
        Assertions.assertThat(messages.get(1).getExpired()).isEqualTo(true);
        Assertions.assertThat(messages.get(2).getExpired()).isEqualTo(false); // 리뷰는 삭제 대상 X
        Assertions.assertThat(messages.get(3).getExpired()).isEqualTo(false); // 시간이 2일 전이 아니라면 삭제 대상 X
    }

}
