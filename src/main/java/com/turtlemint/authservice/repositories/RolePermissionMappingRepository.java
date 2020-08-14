package com.turtlemint.authservice.repositories;

import com.turtlemint.authservice.entity.RolePermissionMapping;
import com.turtlemint.authservice.entity.UserRoleMapping;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RolePermissionMappingRepository extends ReactiveMongoRepository<RolePermissionMapping, ObjectId> {

    @Query(value = "{'role_permissions': ?0}")
    Flux<RolePermissionMapping> findByRoleId(ObjectId roleId);
}
