package com.turtlemint.authservice.repository;


import com.turtlemint.authservice.configuration.DBConfiguration;
import com.turtlemint.authservice.entity.RolePermissionMapping;
import com.turtlemint.authservice.entity.UserRoleMapping;
import com.turtlemint.authservice.repositories.RolePermissionMappingRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@Import(DBConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RolePermissionMappingRepositoryTest {

    @Autowired
    private RolePermissionMappingRepository rolePermissionMappingRepository;

    @BeforeEach
    public void init(){
        rolePermissionMappingRepository.deleteAll().block();
    }

    @AfterAll
    public void destroy(){
        rolePermissionMappingRepository.deleteAll().block();
    }

    @Test
    public void notNull(){
        assertNotNull(rolePermissionMappingRepository);
    }

    @Test
    public void findByUserId(){
        RolePermissionMapping rpm = new RolePermissionMapping(new ObjectId(), new ObjectId());

        rolePermissionMappingRepository.save(rpm).block();

        StepVerifier.create(rolePermissionMappingRepository.findByRoleId(rpm.getRole_permissions()))
                .assertNext(dbRpm -> assertEquals(rpm, dbRpm))
                .verifyComplete();
    }
}
