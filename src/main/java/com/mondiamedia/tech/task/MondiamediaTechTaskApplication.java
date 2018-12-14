package com.mondiamedia.tech.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.mondiamedia.tech.task.config.AppConfig;
import com.mondiamedia.tech.task.config.SpringApplicationContext;

@SpringBootApplication
public class MondiamediaTechTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(MondiamediaTechTaskApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean(name = "AppConfig")
	public AppConfig appConfig() {
		return new AppConfig();
	}
}
