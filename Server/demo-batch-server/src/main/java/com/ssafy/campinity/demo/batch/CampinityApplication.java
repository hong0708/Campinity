package com.ssafy.campinity.demo.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan("com.ssafy.campinity.core")
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.ssafy.campinity"})
@EnableScheduling
@EnableBatchProcessing
public class CampinityApplication {

	public static void main(String[] args) {

		SpringApplication.run(CampinityApplication.class, args);
	}
}
