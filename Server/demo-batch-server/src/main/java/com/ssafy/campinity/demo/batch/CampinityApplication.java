package com.ssafy.campinity.demo.batch;

import com.ssafy.campinity.core.entity.campsite.Amenity;
import com.ssafy.campinity.core.repository.campsite.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.ssafy.campinity.core")
@EnableJpaRepositories("com.ssafy.campinity.core")
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.ssafy.campinity"})
public class CampinityApplication {

	public static void main(String[] args) {

		SpringApplication.run(CampinityApplication.class, args);
	}

}
