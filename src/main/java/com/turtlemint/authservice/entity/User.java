package com.turtlemint.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Document(value = "user")
public class User {

    @Id
    private ObjectId id;
    private String client;
    private String userId;
    private String tenant;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String userId, String client, String tenant){
        this(null,client, userId, tenant, LocalDateTime.now(), LocalDateTime.now());
    }

}
