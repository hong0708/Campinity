package com.ssafy.campinity.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.ssafy.campinity"}, exclude = SecurityAutoConfiguration.class)
@ComponentScan("com.ssafy.campinity.core")
@ComponentScan("com.ssafy.campinity.api")
@EntityScan({"com.ssafy.campinity.core", "com.ssafy.campinity.api"})
@EnableJpaRepositories("com.ssafy.campinity.core")
@EnableJpaAuditing
public class CampinityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampinityApplication.class, args);
	}

}
