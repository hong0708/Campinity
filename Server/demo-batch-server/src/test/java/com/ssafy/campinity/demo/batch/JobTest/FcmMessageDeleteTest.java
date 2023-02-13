package com.ssafy.campinity.demo.batch.JobTest;

import com.ssafy.campinity.core.entity.fcm.FcmMessage;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.fcm.FcmMessageRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.demo.batch.config.BatchDataSourceConfig;
import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
import com.ssafy.campinity.demo.batch.config.TestBatchConfig;
import com.ssafy.campinity.demo.batch.job.FcmMessageDeleteConfig;
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

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FcmMessageDeleteConfig.class, TestBatchConfig.class, BatchDataSourceConfig.class, CampinityDataSourceConfig.class})
@SpringBatchTest
//@ActiveProfiles("test")
public class FcmMessageDeleteTest {

    @Autowired
    FcmMessageRepository fcmMessageRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @DisplayName("Fcm Message delete job 테스트")
    public void FcmMessageDeleteJobTest () throws Exception {

        Member member = Member.builder().uuid(UUID.randomUUID()).email("test@email.com").build();
        Member savedMember = memberRepository.save(member);

        UUID uuid = UUID.randomUUID();
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        FcmMessage fcmMessage = FcmMessage.builder().member(savedMember).uuid(uuid).build();
        FcmMessage savedFm = fcmMessageRepository.save(fcmMessage);
        FcmMessage fcmMessage1 = FcmMessage.builder().member(savedMember).uuid(uuid1).build();
        FcmMessage savedFm1 = fcmMessageRepository.save(fcmMessage1);
        FcmMessage fcmMessage2 = FcmMessage.builder().member(savedMember).uuid(uuid2).build();
        FcmMessage savedFm2 = fcmMessageRepository.save(fcmMessage2);

        savedFm.expired();
        fcmMessageRepository.save(savedFm);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", Long.toString(System.currentTimeMillis())).toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);  // job 실행 결과가 성공인지

        FcmMessage result = fcmMessageRepository.findByUuidAndExpiredIsFalse(uuid).orElse(null);
        FcmMessage result1 = fcmMessageRepository.findByUuidAndExpiredIsFalse(uuid1).orElse(null);
        FcmMessage result2 = fcmMessageRepository.findByUuidAndExpiredIsFalse(uuid2).orElse(null);

        Assertions.assertThat(result != null);
        Assertions.assertThat(result1 == null);
        Assertions.assertThat(result2 == null);

        fcmMessageRepository.deleteAllInBatch(List.of(savedFm, savedFm1, savedFm2));
        memberRepository.delete(savedMember);
    }
}
