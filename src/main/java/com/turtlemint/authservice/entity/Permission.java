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
@Document(value = "permission")
public class Permission {

    @Id
    private ObjectId id;
    private String key;
    private String name;
    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Permission(String key, String name, boolean active){
        this(null, key, name, active, LocalDateTime.now(), LocalDateTime.now());
    }

}
