package com.turtlemint.authservice.repository;

import com.turtlemint.authservice.configuration.DBConfiguration;
import com.turtlemint.authservice.entity.UserRoleMapping;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@Import(DBConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRoleMappingRepositoryTest {

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @BeforeEach
    public void init(){
        userRoleMappingRepository.deleteAll().block();
    }

    @AfterAll
    public void destroy(){
        userRoleMappingRepository.deleteAll().block();
    }

    @Test
    public void notNull(){
        assertNotNull(userRoleMappingRepository);
    }

    @Test
    public void findByUserId(){
        UserRoleMapping urm = new UserRoleMapping(new ObjectId(), new ObjectId());

        userRoleMappingRepository.save(urm).block();

        StepVerifier.create(userRoleMappingRepository.findByUserId(urm.getUserId()))
                .assertNext(dbUrm -> assertEquals(urm, dbUrm))
                .verifyComplete();
    }

    @Test
    public void findByUserIds(){
        UserRoleMapping urm = new UserRoleMapping(new ObjectId(), new ObjectId());
        userRoleMappingRepository.save(urm).block();
        StepVerifier.create(userRoleMappingRepository.findByUserIds(Arrays.asList(urm.getUserId())))
                .assertNext(dbUrm -> assertEquals(urm, dbUrm))
                .verifyComplete();
    }
}
