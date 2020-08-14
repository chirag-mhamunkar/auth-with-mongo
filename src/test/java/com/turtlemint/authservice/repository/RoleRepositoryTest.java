package com.turtlemint.authservice.repository;

import com.turtlemint.authservice.configuration.DBConfiguration;
import com.turtlemint.authservice.entity.Role;
import com.turtlemint.authservice.repositories.RoleRepository;
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
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void init(){
        roleRepository.deleteAll().block();
    }
    
    @AfterAll
    public void destroy(){
        roleRepository.deleteAll().block();
    }

    @Test
    public void notNull(){
        assertNotNull(roleRepository);
    }

    @Test
    public void saveRole(){
        Role role = new Role("NEW_USER", "New User", "turtlemint", true);
        StepVerifier.create(roleRepository.save(role))
                .assertNext(dbRole -> {
                    assertNotNull(dbRole.getId());
                    assertEquals(role.getKey(), dbRole.getKey());
                })
                .verifyComplete();
    }

    @Test
    public void findByKeys(){
        Role role1 = new Role("NEW_USER", "New User", "turtlemint", true);
        Role role2 = new Role("ADMIN", "Admin", "turtlemint", true);
        roleRepository.saveAll(Arrays.asList(role1, role2)).collectList().block();

        StepVerifier.create(roleRepository.findByKeyIn(Arrays.asList("NEW_USER", "ADMIN")).collectList())
                .assertNext(roles -> assertEquals(2, roles.size()))
                .verifyComplete();

    }


}
