package com.turtlemint.authservice.repository;

import com.turtlemint.authservice.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository extends ReactiveMongoRepository<User, ObjectId> {

    @Query(value = "{'userId': {'$in': ?0}}")
    Flux<User> findByUserIds(List<String> userIds);

    @Query(value = "{'userId': ?0, 'client': ?1, 'tenant': ?2}")
    Mono<User> findByUserIdClientTenant(String userId, String client, String tenant);


}
