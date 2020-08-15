package com.turtlemint.authservice.model;

import com.turtlemint.authservice.entity.Permission;
import com.turtlemint.authservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleModel {
    private String id;
    private String key;
    private String name;
    private String tenant;
    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<PermissionModel> permissions;

    //THIS SYNCHRONIZATION CAN BE ENHANCED BY DOUBLE WAY CHECKING
    public synchronized void addPermission(PermissionModel permissionModel){
        if(Objects.isNull(permissions))
            permissions = new ArrayList<>();
        permissions.add(permissionModel);
    }

    public static RoleModel from(Role role){
        return RoleModel.builder()
                .id(role.getId().toHexString())
                .key(role.getKey())
                .name(role.getName())
                .tenant(role.getTenant())
                .active(role.isActive())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }

    public static RoleModel from(Role role, List<PermissionModel> permissions){
        RoleModel roleModel = from(role);
        roleModel.setPermissions(permissions);
        return roleModel;
    }
}
