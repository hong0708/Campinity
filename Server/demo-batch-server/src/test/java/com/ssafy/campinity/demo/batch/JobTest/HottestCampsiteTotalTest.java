//package com.ssafy.campinity.demo.batch.JobTest;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.campinity.core.dto.CampsiteRankingResDTO;
//import com.ssafy.campinity.core.entity.campsite.Campsite;
//import com.ssafy.campinity.core.entity.fcm.FcmToken;
//import com.ssafy.campinity.core.entity.member.Member;
//import com.ssafy.campinity.core.entity.message.Message;
//import com.ssafy.campinity.core.entity.question.Question;
//import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
//import com.ssafy.campinity.core.repository.fcm.FcmTokenRepository;
//import com.ssafy.campinity.core.repository.member.MemberRepository;
//import com.ssafy.campinity.core.repository.message.MessageRepository;
//import com.ssafy.campinity.core.repository.question.QuestionRepository;
//import com.ssafy.campinity.core.repository.redis.RedisDao;
//import com.ssafy.campinity.demo.batch.config.BatchDataSourceConfig;
//import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
//import com.ssafy.campinity.demo.batch.config.TestBatchConfig;
//import com.ssafy.campinity.demo.batch.job.HottestCampsiteConfig;
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
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {HottestCampsiteConfig.class, TestBatchConfig.class, BatchDataSourceConfig.class, CampinityDataSourceConfig.class})
//@SpringBatchTest
//public class HottestCampsiteTotalTest {
//
//    @Autowired
//    MessageRepository messageRepository;
//
//    @Autowired
//    CampsiteRepository campsiteRepository;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    QuestionRepository questionRepository;
//
//    @Autowired
//    FcmTokenRepository fcmTokenRepository;
//
//    @Autowired
//    RedisDao redisDao;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Test
//    @DisplayName("hottest campsite job 통합 테스트")
//    public void hottestCampsiteJobTotalTest () throws Exception {
//        Campsite campsite1 = Campsite.builder().uuid(UUID.randomUUID()).campName("test")
//                .amenities(new ArrayList<>()).amenities(new ArrayList<>()).messages(new ArrayList<>())
//                .caravanFclties(new ArrayList<>()).glampFclties(new ArrayList<>()).industries(new ArrayList<>())
//                .openSeasons(new ArrayList<>()).themes(new ArrayList<>()).build();
//
//        Campsite campsite2 = Campsite.builder().uuid(UUID.randomUUID()).campName("test")
//                .amenities(new ArrayList<>()).amenities(new ArrayList<>()).messages(new ArrayList<>())
//                .caravanFclties(new ArrayList<>()).glampFclties(new ArrayList<>()).industries(new ArrayList<>())
//                .openSeasons(new ArrayList<>()).themes(new ArrayList<>()).build();
//
//        Campsite campsite3 = Campsite.builder().uuid(UUID.randomUUID()).campName("test")
//                .amenities(new ArrayList<>()).amenities(new ArrayList<>()).messages(new ArrayList<>())
//                .caravanFclties(new ArrayList<>()).glampFclties(new ArrayList<>()).industries(new ArrayList<>())
//                .openSeasons(new ArrayList<>()).themes(new ArrayList<>()).build();
//
//        Campsite campsite4 = Campsite.builder().uuid(UUID.randomUUID()).campName("test")
//                .amenities(new ArrayList<>()).amenities(new ArrayList<>()).messages(new ArrayList<>())
//                .caravanFclties(new ArrayList<>()).glampFclties(new ArrayList<>()).industries(new ArrayList<>())
//                .openSeasons(new ArrayList<>()).themes(new ArrayList<>()).build();
//
//        Campsite campsite5 = Campsite.builder().uuid(UUID.randomUUID()).campName("test")
//                .amenities(new ArrayList<>()).amenities(new ArrayList<>()).messages(new ArrayList<>())
//                .caravanFclties(new ArrayList<>()).glampFclties(new ArrayList<>()).industries(new ArrayList<>())
//                .openSeasons(new ArrayList<>()).themes(new ArrayList<>()).build();
//
//        Campsite campsite6 = Campsite.builder().uuid(UUID.randomUUID()).campName("test")
//                .amenities(new ArrayList<>()).amenities(new ArrayList<>()).messages(new ArrayList<>())
//                .caravanFclties(new ArrayList<>()).glampFclties(new ArrayList<>()).industries(new ArrayList<>())
//                .openSeasons(new ArrayList<>()).themes(new ArrayList<>()).build();
//
//        Campsite savedCampstie1 = campsiteRepository.save(campsite1);  // 질문: 2개, 메세지: 1개, 토큰: 1개
//        System.out.println("1 : " + savedCampstie1.getUuid());
//        Campsite savedCampstie2 = campsiteRepository.save(campsite2);  // 질문: 1개, 메세지: 2개, 토큰: 1개
//        System.out.println("2 : " + savedCampstie1.getUuid());
//        Campsite savedCampstie3 = campsiteRepository.save(campsite3);  // 질문: 1개, 메세지: 1개, 토큰: 1개
//        System.out.println("3 : " + savedCampstie1.getUuid());
//        Campsite savedCampstie4 = campsiteRepository.save(campsite4);  // 질문: 1개, 메세지: 0개, 토큰: 0개
//        System.out.println("4 : " + savedCampstie1.getUuid());
//        Campsite savedCampstie5 = campsiteRepository.save(campsite5);  // 질문: 0개, 메세지: 1개, 토큰: 0개
//        System.out.println("5 : " + savedCampstie1.getUuid());
//        Campsite savedCampstie6 = campsiteRepository.save(campsite6);  // 질문: 0개, 메세지: 0개, 토큰: 0개
//        System.out.println("6 : " + savedCampstie1.getUuid());
//
//        Member member = Member.builder().uuid(UUID.randomUUID()).email("test@email.com").build();
//        Member savedMember = memberRepository.save(member);
//
//        LocalDateTime date = LocalDateTime.now().minusDays(3);
//
//
//        Message message1 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(savedCampstie1)
//                .member(savedMember).messageCategory("자유").build();
//        Message savedMessage1 = messageRepository.save(message1);
//        savedMessage1.setCreatedAt(date);
//        Message m1 = messageRepository.save(savedMessage1);
//
//        Message message2 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(savedCampstie2)
//                .member(savedMember).messageCategory("자유").build();
//        Message savedMessage2 = messageRepository.save(message2);
//        savedMessage2.setCreatedAt(date);
//        Message m2 = messageRepository.save(savedMessage2);
//
//        Message message3 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(savedCampstie2)
//                .member(savedMember).messageCategory("자유").build();
//        Message savedMessage3 = messageRepository.save(message3);
//        savedMessage3.setCreatedAt(date);
//        Message m3 = messageRepository.save(savedMessage3);
//
//        Message message4 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(savedCampstie3)
//                .member(savedMember).messageCategory("자유").build();
//        Message savedMessage4 = messageRepository.save(message4);
//        savedMessage4.setCreatedAt(date);
//        Message m4 = messageRepository.save(savedMessage4);
//
//        Message message5 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(savedCampstie5)
//                .member(savedMember).messageCategory("자유").build();
//        Message savedMessage5 = messageRepository.save(message5);
//        savedMessage5.setCreatedAt(date);
//        Message m5 = messageRepository.save(savedMessage5);
//
//
//        Question question1 = Question.builder().campsite(savedCampstie1).member(savedMember).
//                uuid(UUID.randomUUID()).content("test").build();
//        Question savedQuestion1 = questionRepository.save(question1);
//        savedQuestion1.setCreatedAt(date);
//        Question q1 = questionRepository.save(savedQuestion1);
//
//        Question question2 = Question.builder().campsite(savedCampstie1).member(savedMember).
//                uuid(UUID.randomUUID()).content("test").build();
//        Question savedQuestion2 = questionRepository.save(question2);
//        savedQuestion2.setCreatedAt(date);
//        Question q2 = questionRepository.save(savedQuestion2);
//
//        Question question3 = Question.builder().campsite(savedCampstie2).member(savedMember).
//                uuid(UUID.randomUUID()).content("test").build();
//        Question savedQuestion3 = questionRepository.save(question3);
//        savedQuestion3.setCreatedAt(date);
//        Question q3 = questionRepository.save(savedQuestion3);
//
//        Question question4 = Question.builder().campsite(savedCampstie3).member(savedMember).
//                uuid(UUID.randomUUID()).content("test").build();
//        Question savedQuestion4 = questionRepository.save(question4);
//        savedQuestion4.setCreatedAt(date);
//        Question q4 = questionRepository.save(savedQuestion4);
//
//        Question question5 = Question.builder().campsite(savedCampstie4).member(savedMember).
//                uuid(UUID.randomUUID()).content("test").build();
//        Question savedQuestion5 = questionRepository.save(question5);
//        savedQuestion5.setCreatedAt(date);
//        Question q5 = questionRepository.save(savedQuestion5);
//
//
//        FcmToken fcmToken1 = FcmToken.builder().campsiteUuid(savedCampstie1.getUuid().toString()).member(savedMember).build();
//        FcmToken fcmToken2 = FcmToken.builder().campsiteUuid(savedCampstie2.getUuid().toString()).member(savedMember).build();
//        FcmToken fcmToken3 = FcmToken.builder().campsiteUuid(savedCampstie3.getUuid().toString()).member(savedMember).build();
//
//        fcmTokenRepository.save(fcmToken1);
//        fcmTokenRepository.save(fcmToken2);
//        fcmTokenRepository.save(fcmToken3);
//
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("time", Long.toString(System.currentTimeMillis())).toJobParameters();
//
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
//
//        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);  // job의 실행결과가 complete인지
//
//        String highestCampsite = redisDao.getValues("hottestCampsite");
//        List<CampsiteRankingResDTO> result = objectMapper.readValue(highestCampsite, new TypeReference<List<CampsiteRankingResDTO>>() {});
//
//        Assertions.assertThat(result.get(0).getCampsiteId()).isEqualTo(savedCampstie1.getUuid().toString());
//        Assertions.assertThat(result.get(1).getCampsiteId()).isEqualTo(savedCampstie2.getUuid().toString());
//        Assertions.assertThat(result.get(2).getCampsiteId()).isEqualTo(savedCampstie3.getUuid().toString());
//        Assertions.assertThat(result.get(3).getCampsiteId()).isEqualTo(savedCampstie4.getUuid().toString());
//        Assertions.assertThat(result.get(4).getCampsiteId()).isEqualTo(savedCampstie5.getUuid().toString());
//    }
//
//}
