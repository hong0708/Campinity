package com.ssafy.campinity.demo.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.ssafy.campinity.demo.batch", "com.ssafy.campinity.core"})
@ComponentScan("com.ssafy.campinity.core")
@ComponentScan("com.ssafy.campinity.demo.batch")
@EnableScheduling
@EnableBatchProcessing
public class CampinityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampinityApplication.class, args);
	}
}
