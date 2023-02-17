package com.ssafy.campinity.demo.batch.multiDatabase;

import com.ssafy.campinity.core.entity.campsite.Campsite;
import com.ssafy.campinity.core.repository.campsite.CampsiteRepository;
import com.ssafy.campinity.demo.batch.config.BatchDataSourceConfig;
import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
import com.ssafy.campinity.demo.batch.config.TestBatchConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest(classes = {TestBatchConfig.class, BatchDataSourceConfig.class, CampinityDataSourceConfig.class})
@RunWith(SpringRunner.class)
@EnableTransactionManagement
@ActiveProfiles("test")
public class MultiDatabaseTest {

    @Autowired
    CampsiteRepository campsiteRepository;


    @Test
    @DisplayName("campinity db 접근 테스트")
    @Transactional("campinityTransactionManager")
    public void campinityDatabaseTest () {
        Campsite campsite = Campsite.builder().uuid(UUID.randomUUID()).campName("test").build();

        Campsite savedCampsite = campsiteRepository.save(campsite);

        Optional<Campsite> searchedCampsite = campsiteRepository.findByUuid(savedCampsite.getUuid());

        Assertions.assertThat(searchedCampsite.isEmpty()).isEqualTo(false);
    }

}
