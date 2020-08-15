package com.turtlemint.authservice.repositories;

import com.turtlemint.authservice.entity.RolePermissionMapping;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RolePermissionMappingRepository extends ReactiveMongoRepository<RolePermissionMapping, ObjectId> {

    @Query(value = "{'role_permissions': ?0}")
    Flux<RolePermissionMapping> findByRoleId(ObjectId roleId);

    //TODO FOR SOME REASON, THIS IS NOT WORKING
    @Query(value = "{'role_permissions': {'$in': ?0}}")
    Flux<RolePermissionMapping> getByRoleIds(List<ObjectId> roleIds);
}
