package com.turtlemint.authservice.repository;

import com.turtlemint.authservice.entity.Permission;
import com.turtlemint.authservice.entity.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PermissionRepository extends ReactiveMongoRepository<Permission, ObjectId> {

    Mono<Role> findByKey(String key);
    Flux<Role> findByKeyIn(List<String> keys);
}
