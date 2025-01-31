package com.chewingclass.chewingclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.chewingclass.chewingclass.entity")
@EnableJpaRepositories(basePackages = "com.chewingclass.chewingclass.repository")
public class ChewingclassApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChewingclassApplication.class, args);
	}
}