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
@Document(value = "role_users__user_roles")
public class UserRoleMapping {

    @Id
    private ObjectId id;
    private ObjectId user_roles;
    private ObjectId role_users;

    public UserRoleMapping(ObjectId userId, ObjectId roleId){
        this(null, userId, roleId);
    }

}
