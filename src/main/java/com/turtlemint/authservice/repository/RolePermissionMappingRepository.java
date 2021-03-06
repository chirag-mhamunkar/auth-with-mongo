package com.turtlemint.authservice.repository;

import com.turtlemint.authservice.entity.RolePermissionMapping;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RolePermissionMappingRepository extends ReactiveMongoRepository<RolePermissionMapping, ObjectId> {

    @Query(value = "{'role_permissions': ?0}")
    Flux<RolePermissionMapping> findByRoleId(ObjectId roleId);

    @Query(value = "{'role_permissions': {'$in': ?0}}")
    Flux<RolePermissionMapping> findByRoleIds(List<ObjectId> roleIds);
}
