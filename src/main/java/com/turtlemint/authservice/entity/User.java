package com.turtlemint.authservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;


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

}
