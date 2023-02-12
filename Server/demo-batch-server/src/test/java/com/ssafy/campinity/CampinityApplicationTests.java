package com.ssafy.campinity;

import com.ssafy.campinity.core.CampinityApplication;
import com.ssafy.campinity.demo.batch.config.CampinityDataSourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootTest(classes = {CampinityApplication.class, CampinityDataSourceConfig.class})
class CampinityApplicationTests {

	@Test
	void contextLoads() {
	}

}
