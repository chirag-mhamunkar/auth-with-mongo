package com.turtlemint.authservice.model;

import com.turtlemint.authservice.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionModel {

    private String id;
    private String key;
    private String name;
    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static PermissionModel from(Permission permission){
        return PermissionModel.builder()
                .id(permission.getId().toHexString())
                .key(permission.getKey())
                .name(permission.getName())
                .active(permission.isActive())
                .createdAt(permission.getCreatedAt())
                .updatedAt(permission.getUpdatedAt())
                .build();
    }
}
