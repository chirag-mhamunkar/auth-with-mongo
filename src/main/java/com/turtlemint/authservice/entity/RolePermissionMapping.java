package com.turtlemint.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Document(value = "permission_roles__role_permissions")
public class RolePermissionMapping {

    @Id
    private ObjectId id;
    private ObjectId role_permissions;
    private ObjectId permission_roles;

    public RolePermissionMapping(ObjectId roleId, ObjectId permissionId){
        this(null, roleId, permissionId);
    }
}
