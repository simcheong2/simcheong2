package com.example.simcheong2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Simcheong2Application {

	public static void main(String[] args) {
		SpringApplication.run(Simcheong2Application.class, args);
	}

}
