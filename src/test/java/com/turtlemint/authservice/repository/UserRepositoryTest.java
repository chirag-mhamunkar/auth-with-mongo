package com.turtlemint.authservice.repository;

import com.turtlemint.authservice.configuration.DBConfiguration;
import com.turtlemint.authservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class will connect to local mongo instance, db would be 'test'
 */
@Slf4j
@DataMongoTest
@Import(DBConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        log.info("BeforeEach: Removing all entries from collection");
        userRepository.deleteAll().block();
    }


    @AfterAll
    public void destroy(){
        log.info("AfterAll: Removing all entries from collection");
        userRepository.deleteAll().block();
    }

    @Test
    public void userRepositoryNotNull(){
        Assertions.assertNotNull(userRepository);
    }

    @Test
    public void saveUser(){
        User user1 = new User("59b9421de4b0a459d044cd3c", "MINTPRO", "TURTLEMINT");
        User user2 = new User("59b9421de4b0a459d044cd40", "MINTPRO", "TURTLEMINT");
        StepVerifier.create(userRepository.saveAll(Arrays.asList(user1, user2)).log())
                .assertNext(user -> Assertions.assertNotNull(user.getId()))
                .expectComplete();
        //.verifyComplete();
    }

    @Test
    public void findUserIdsTest(){
        User user1 = new User("59b9421de4b0a459d044cd3c", "MINTPRO", "TURTLEMINT");
        User user2 = new User("59b9421de4b0a459d044cd40", "MINTPRO", "TURTLEMINT");

        userRepository.saveAll(Arrays.asList(user1, user2)).collectList().block();

        StepVerifier.create(userRepository.findByUserIds(Arrays.asList(user1.getUserId(), user2.getUserId())).collectList())
                .assertNext(users -> Assertions.assertEquals(2, users.size()))
                .verifyComplete();
    }

    @Test
    public void findByUserIdClientTenantFound(){
        User u = new User("59b9421de4b0a459d044cd3c", "MINTPRO", "TURTLEMINT");
        userRepository.save(u).block();

        log.info("Java: {}", u);

        StepVerifier.create(userRepository.findByUserIdClientTenant(u.getUserId(), u.getClient(), u.getTenant()))
                .assertNext(dbUser -> {
                    assertNotNull(dbUser.getId());
                    assertEquals(u.getUserId(), dbUser.getUserId());
                    assertEquals(u.getClient(), dbUser.getClient());
                    assertEquals(u.getTenant(), dbUser.getTenant());
                    log.info("DB: {}", dbUser);

                    //Mongo DB doesn't hold nano seconds
                    assertEquals(u.getCreatedAt().minusNanos(u.getCreatedAt().getNano()), dbUser.getCreatedAt().minusNanos(dbUser.getCreatedAt().getNano()));
                    assertEquals(u.getUpdatedAt().minusNanos(u.getUpdatedAt().getNano()), dbUser.getUpdatedAt().minusNanos(dbUser.getUpdatedAt().getNano()));
                })
                .verifyComplete();
    }

    @Test
    public void findByUserIdClientTenantNOTFound(){
        User u = new User("59b9421de4b0a459d044cd3c", "MINTPRO", "TURTLEMINT");
        userRepository.save(u).block();

        StepVerifier.create(userRepository.findByUserIdClientTenant(u.getUserId(), u.getClient(), "HDFC"))
                .verifyComplete();
    }

    @Test
    public void findByUserIdClientTenantNOTFoundBlock(){
        User u = new User("59b9421de4b0a459d044cd3c", "MINTPRO", "TURTLEMINT");
        userRepository.save(u).block();
        User user = userRepository.findByUserIdClientTenant(u.getUserId(), u.getClient(), "HDFC").block();
        assertNull(user);
    }
}
