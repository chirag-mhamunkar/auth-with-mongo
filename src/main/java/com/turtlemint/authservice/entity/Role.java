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
@Document(value = "role")
public class Role {

    @Id
    private ObjectId id;
    private String key;
    private String name;
    private String tenant;
    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Role(String key, String name, String tenant, boolean active){
        this(null, key, name, tenant, active, LocalDateTime.now(), LocalDateTime.now());
    }

}
