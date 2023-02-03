package com.ssafy.campinity.demo.batch.multiDatabase;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.entity.message.Message;
import com.ssafy.campinity.core.entity.message.MessageCategory;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.core.repository.message.MessageRepository;
import com.ssafy.campinity.demo.batch.entity.TestEntity;
import com.ssafy.campinity.demo.batch.repository.TestEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableTransactionManagement
public class MultiDatabaseTest {

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    TestEntityRepository testEntityRepository;

    @Test
    @DisplayName("campinity db 접근 테스트")
    @Transactional("campinityTransactionManager")
    public void campinityDatabaseTest (){
        Campsite campsite = Campsite.builder().uuid(UUID.randomUUID()).campName("test").build();

        Campsite savedCampsite = campsiteRepository.save(campsite);

        Optional<Campsite> searchedCampsite = campsiteRepository.findByUuid(savedCampsite.getUuid());

        Assertions.assertThat(searchedCampsite.isEmpty()).isEqualTo(false);
    }

    @Test
    @DisplayName("배치 db")
    @Transactional("batchTransactionManager")
    public void batchDatabaseTest (){

        TestEntity mh1 = TestEntity.builder().name("mh1").build();

        TestEntity saved = testEntityRepository.save(mh1);

        Optional<TestEntity> byId = testEntityRepository.findById(saved.getId());

        Assertions.assertThat(byId.isEmpty()).isEqualTo(false);

    }
}
