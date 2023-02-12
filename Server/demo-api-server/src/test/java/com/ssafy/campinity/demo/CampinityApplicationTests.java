package com.ssafy.campinity.demo;

import com.ssafy.campinity.core.CampinityApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = CampinityApplication.class)
@ActiveProfiles("test")
class CampinityApplicationTests {
	@Test
	void contextLoads() {
	}

}
