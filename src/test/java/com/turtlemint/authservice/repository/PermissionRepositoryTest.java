package com.turtlemint.authservice.repository;

import com.turtlemint.authservice.configuration.DBConfiguration;
import com.turtlemint.authservice.entity.Permission;
import com.turtlemint.authservice.entity.Role;
import com.turtlemint.authservice.repositories.PermissionRepository;
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
public class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @BeforeEach
    public void init(){
        permissionRepository.deleteAll().block();
    }

    @AfterAll
    public void destroy(){
        permissionRepository.deleteAll().block();
    }

    @Test
    public void notNull(){
        assertNotNull(permissionRepository);
    }

    @Test
    public void saveRole(){
        Permission permission = new Permission("TAB_CUSTOMER_READ", "TAB_CUSTOMER_READ", true);
        StepVerifier.create(permissionRepository.save(permission))
                .assertNext(dbRole -> {
                    assertNotNull(dbRole.getId());
                    assertEquals(permission.getKey(), dbRole.getKey());
                })
                .verifyComplete();
    }

    @Test
    public void findByKeys(){
        Permission permission1 = new Permission("TAB_CUSTOMER_READ", "TAB_CUSTOMER_READ", true);
        Permission permission2 = new Permission("ADMIN_PERMISSION", "ADMIN_PERMISSION", true);
        permissionRepository.saveAll(Arrays.asList(permission1, permission2)).collectList().block();

        StepVerifier.create(permissionRepository.findByKeyIn(Arrays.asList("TAB_CUSTOMER_READ", "ADMIN_PERMISSION", "TEST")).collectList())
                .assertNext(roles -> assertEquals(2, roles.size()))
                .verifyComplete();

    }
}
