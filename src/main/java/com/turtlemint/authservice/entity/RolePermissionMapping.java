package com.turtlemint.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Document(value = "permission_roles__role_permissions")
public class RolePermissionMapping {

    @Id
    private ObjectId id;

    @Field("role_permissions")
    private ObjectId roleId;

    @Field("permission_roles")
    private ObjectId permissionId;

    public RolePermissionMapping(ObjectId roleId, ObjectId permissionId){
        this(null, roleId, permissionId);
    }
}
