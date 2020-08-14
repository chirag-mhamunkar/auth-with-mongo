package com.turtlemint.authservice;

import com.turtlemint.authservice.entity.User;
import com.turtlemint.authservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
//@EnableReactiveMongoRepositories //Apparently this annotation is not needed
public class AuthWithMongoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AuthWithMongoApplication.class, args);
		//test1(context);
		test2(context);
		log.info("Hello world");
	}

	private static void test2(ApplicationContext context) {
		UserRepository repository = context.getBean(UserRepository.class);
		List<User> users = repository.findByUserIds(Arrays.asList("59b9421de4b0a459d044cd3c", "59b9421de4b0a459d044cd40"))
				.collectList()
				.block();

		log.info("Found {} users", users.size());
		log.info("{}", users);
	}

	private static void test1(ApplicationContext context) {
		UserRepository repository = context.getBean(UserRepository.class);
		User user = new User();
		//user.setId(new ObjectId("5a1a790b3212be9f29b54520"));
		user.setUserId("59b9421de4b0a459d044cd3c");
		Example<User> userExample = Example.of(user);
		User dbUser =
				repository.findOne(userExample)
				//repository.findById(new ObjectId("5a1a790b3212be9f29b5452a"))
				.log()
				.doOnSuccess(u -> log.info("Found: {}", u))
				.block();
		dbUser.setId(null);
		dbUser.setCreatedAt(LocalDateTime.now());
		dbUser.setUpdatedAt(LocalDateTime.now());
		repository.save(dbUser).
				doOnSuccess(u -> log.info("Saved: {}", u))
				.block();
		log.info("Found: {}", dbUser);
	}

}
