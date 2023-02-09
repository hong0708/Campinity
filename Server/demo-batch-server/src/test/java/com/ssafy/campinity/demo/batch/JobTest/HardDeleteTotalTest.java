package com.ssafy.campinity.demo.batch.JobTest;

import com.ssafy.campinity.core.entity.MyCollection.MyCollection;
import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.entity.message.LikeMessage;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.repository.message.LikeMessageRepository;
import com.ssafy.campinity.core.repository.message.MessageRepository;
import com.ssafy.campinity.core.repository.myCollection.MyCollectionRepository;
import com.ssafy.campinity.demo.batch.config.BatchDataSourceConfig;
import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
import com.ssafy.campinity.demo.batch.config.TestBatchConfig;
import com.ssafy.campinity.demo.batch.job.HardDeleteConfig;
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
@SpringBootTest(classes = {HardDeleteConfig.class, TestBatchConfig.class, BatchDataSourceConfig.class, CampinityDataSourceConfig.class})
@SpringBatchTest
public class HardDeleteTotalTest {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MyCollectionRepository myCollectionRepository;

    @Autowired
    LikeMessageRepository likeMessageRepository;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @DisplayName("hard delete job 통합 테스트(step1. message delete test)")
    public void hardDeleteJobTotalTest1 () throws Exception {
        Campsite campsite = Campsite.builder().uuid(UUID.randomUUID()).campName("test")
                .amenities(new ArrayList<>()).amenities(new ArrayList<>()).messages(new ArrayList<>())
                .caravanFclties(new ArrayList<>()).glampFclties(new ArrayList<>()).industries(new ArrayList<>())
                .openSeasons(new ArrayList<>()).themes(new ArrayList<>()).build();

        Member member = Member.builder().uuid(UUID.randomUUID()).email("test@email.com").build();

        Campsite savedCampsite = campsiteRepository.save(campsite);
        Member savedMember = memberRepository.save(member);

        LocalDateTime date = LocalDateTime.now().minusDays(3);

        Message message1 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(campsite).
                member(savedMember).messageCategory("자유").build();
        Message savedMessage1 = messageRepository.save(message1);
        savedMessage1.setCreatedAt(date);
        savedMessage1.setExpired(true);
        Message m1 = messageRepository.save(savedMessage1);

        Message message2 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(campsite).
                member(savedMember).messageCategory("자유").build();
        Message savedMessage2 = messageRepository.save(message2);
        savedMessage2.setCreatedAt(date);
        savedMessage2.setExpired(true);
        Message m2 = messageRepository.save(savedMessage2);

        Message message3 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(campsite).
                member(savedMember).messageCategory("자유").build();
        Message m3 = messageRepository.save(message3);

        Message message4 = Message.builder().uuid(UUID.randomUUID()).imagePath("").campsite(campsite).
                member(savedMember).messageCategory("리뷰").build();
        Message savedMessage4 = messageRepository.save(message4);
        savedMessage4.setCreatedAt(date);
        savedMessage4.setExpired(true);
        Message m4 = messageRepository.save(savedMessage4);

        LikeMessage likeMessage1 = LikeMessage.builder().message(m1).member(savedMember).build();
        LikeMessage likeMessage2 = LikeMessage.builder().message(m2).member(savedMember).build();
        LikeMessage likeMessage3 = LikeMessage.builder().message(m3).member(savedMember).build();
        LikeMessage likeMessage4 = LikeMessage.builder().message(m4).member(savedMember).build();

        likeMessageRepository.save(likeMessage1);
        likeMessageRepository.save(likeMessage2);
        likeMessageRepository.save(likeMessage3);
        likeMessageRepository.save(likeMessage4);


        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", Long.toString(System.currentTimeMillis())).toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);  // job 실행 결과가 성공인지

        List<Message> messages = messageRepository.findByCampsite_id(savedCampsite.getId()); // message가 정상적으로 삭제가 되었는지
        Assertions.assertThat(messages.size()).isEqualTo(1);

        List<LikeMessage> likeMessages = likeMessageRepository.findByMember(savedMember); // 연관관계가 정상적으로 삭제되었는지 확인
        Assertions.assertThat(likeMessages.size()).isEqualTo(1);

        likeMessageRepository.deleteAllInBatch(likeMessages);
        messageRepository.deleteAllInBatch(messages);
        memberRepository.delete(savedMember);
        campsiteRepository.delete(savedCampsite);
    }

    @Test
    @DisplayName("hard delete job 통합 테스트(step2. myCollection delete test)")
    public void hardDeleteJobTotalTest2 () throws Exception {
        Campsite campsite = Campsite.builder().uuid(UUID.randomUUID()).campName("test")
                .amenities(new ArrayList<>()).amenities(new ArrayList<>()).messages(new ArrayList<>())
                .caravanFclties(new ArrayList<>()).glampFclties(new ArrayList<>()).industries(new ArrayList<>())
                .openSeasons(new ArrayList<>()).themes(new ArrayList<>()).build();

        Member member = Member.builder().uuid(UUID.randomUUID()).email("test@email.com").build();

        Campsite savedCampsite = campsiteRepository.save(campsite);
        Member savedMember = memberRepository.save(member);

        LocalDateTime date = LocalDateTime.now().minusDays(3);

        MyCollection myCollection1 = MyCollection.builder().uuid(UUID.randomUUID()).campsiteName("batch test").content("test").
                member(savedMember).imagePath("").build();

        MyCollection myCollection2 = MyCollection.builder().uuid(UUID.randomUUID()).campsiteName("batch test").content("test").
                member(savedMember).imagePath("").build();

        MyCollection myCollection3 = MyCollection.builder().uuid(UUID.randomUUID()).campsiteName("batch test").content("test").
                member(savedMember).imagePath("").build();

        myCollection1.setExpired(true);
        myCollection2.setExpired(true);
        myCollection3.setExpired(false);

        MyCollection savedMyCollection1 = myCollectionRepository.save(myCollection1);
        MyCollection savedmyCollection2 = myCollectionRepository.save(myCollection2);
        MyCollection savedmyCollection3 = myCollectionRepository.save(myCollection3);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", Long.toString(System.currentTimeMillis())).toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);  // job 실행 결과가 성공인지

        List<MyCollection> myCollections = myCollectionRepository.findByMember(savedMember); // message가 정상적으로 삭제가 되었는지
        Assertions.assertThat(myCollections.size()).isEqualTo(1);

        Assertions.assertThat(myCollections.get(0).getUuid()).isEqualTo(myCollection3.getUuid());

        myCollectionRepository.deleteAllInBatch(myCollections);
        memberRepository.delete(savedMember);
        campsiteRepository.delete(savedCampsite);
    }
}
