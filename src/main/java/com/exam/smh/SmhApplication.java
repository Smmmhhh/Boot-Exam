package com.exam.smh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmhApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmhApplication.class, args);
	}

}
