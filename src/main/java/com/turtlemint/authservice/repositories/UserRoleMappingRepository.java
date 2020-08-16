package com.turtlemint.authservice.repositories;

import com.turtlemint.authservice.entity.Role;
import com.turtlemint.authservice.entity.UserRoleMapping;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface UserRoleMappingRepository extends ReactiveMongoRepository<UserRoleMapping, ObjectId> {

    @Query(value = "{'user_roles': ?0}")
    Flux<UserRoleMapping> findByUserId(ObjectId userId);

    @Query(value = "{'user_roles': {'$in': ?0}}")
    Flux<UserRoleMapping> findByUserIds(List<ObjectId> userId);
}
