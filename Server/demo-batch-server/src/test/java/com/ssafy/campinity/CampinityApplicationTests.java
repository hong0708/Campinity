package com.ssafy.campinity;

import com.ssafy.campinity.core.CampinityApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootTest(classes = CampinityApplication.class)
class CampinityApplicationTests {

	@Test
	void contextLoads() {
	}

}
