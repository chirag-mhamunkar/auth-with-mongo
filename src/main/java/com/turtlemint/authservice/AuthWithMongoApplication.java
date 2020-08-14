package com.turtlemint.authservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
//@EnableReactiveMongoRepositories //Apparently this annotation is not needed
public class AuthWithMongoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AuthWithMongoApplication.class, args);
		log.info("Hello world");
	}
}
