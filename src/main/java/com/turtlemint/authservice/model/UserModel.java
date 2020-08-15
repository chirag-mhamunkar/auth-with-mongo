package com.turtlemint.authservice.model;

import com.turtlemint.authservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserModel {

    private String id;
    private String client;
    private String userId;
    private String tenant;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    List<RoleModel> roles;

    public static UserModel from(User user){
        return UserModel.builder()
                .id(user.getId().toHexString())
                .client(user.getClient())
                .userId(user.getUserId())
                .tenant(user.getTenant())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
