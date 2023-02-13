//package com.ssafy.campinity.demo.batch.JobTest;
//
//import com.ssafy.campinity.core.entity.fcm.FcmToken;
//import com.ssafy.campinity.core.entity.member.Member;
//import com.ssafy.campinity.core.repository.fcm.FcmTokenRepository;
//import com.ssafy.campinity.core.repository.member.MemberRepository;
//import com.ssafy.campinity.demo.batch.config.BatchDataSourceConfig;
//import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
//import com.ssafy.campinity.demo.batch.config.TestBatchConfig;
//import com.ssafy.campinity.demo.batch.job.FcmTokenDeleteConfig;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.batch.core.BatchStatus;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {FcmTokenDeleteConfig.class, TestBatchConfig.class, BatchDataSourceConfig.class, CampinityDataSourceConfig.class})
//@SpringBatchTest
//@ActiveProfiles("test")
//public class FcmTokenDeleteTest {
//
//    @Autowired
//    FcmTokenRepository fcmTokenRepository;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Test
//    @DisplayName("FcmToken delete job 테스트")
//    public void FcmTokenDeleteJobTest () throws Exception {
//
//        Member member = Member.builder().uuid(UUID.randomUUID()).email("test@email.com").build();
//
//        Member savedMember = memberRepository.save(member);
//
//        LocalDate date = LocalDate.now().minusMonths(2);
//
//        FcmToken fcmToken = FcmToken.builder().token("testToken").member(savedMember).expiredDate(date).build();
//        FcmToken ft = fcmTokenRepository.save(fcmToken);
//
//        FcmToken fcmToken1 = FcmToken.builder().token("testToken").member(savedMember).expiredDate(LocalDate.now().plusMonths(1)).build();
//        FcmToken ft1 = fcmTokenRepository.save(fcmToken);
//
//        FcmToken fcmToken2 = FcmToken.builder().token("testToken").member(savedMember).expiredDate(date).build();
//        FcmToken ft2 = fcmTokenRepository.save(fcmToken);
//
//
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("time", Long.toString(System.currentTimeMillis())).toJobParameters();
//
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
//
//        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);  // job 실행 결과가 성공인지
//
//        FcmToken result1 = fcmTokenRepository.findById(ft.getId()).orElse(null);
//        FcmToken result2 = fcmTokenRepository.findById(ft1.getId()).orElse(null);
//        FcmToken result3 = fcmTokenRepository.findById(ft2.getId()).orElse(null);
//
//        Assertions.assertThat(result1 == null);
//        Assertions.assertThat(result2 != null);
//        Assertions.assertThat(result3 == null);
//
//        fcmTokenRepository.deleteAllInBatch(List.of(ft, ft1, ft2));
//        memberRepository.delete(savedMember);
//    }
//}
