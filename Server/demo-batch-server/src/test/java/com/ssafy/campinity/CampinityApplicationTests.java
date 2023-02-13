package com.ssafy.campinity;

import com.ssafy.campinity.core.CampinityApplication;
import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = {CampinityApplication.class, CampinityDataSourceConfig.class})
@ActiveProfiles("test")
class CampinityApplicationTests {

	@Test
	void contextLoads() {
	}

}
