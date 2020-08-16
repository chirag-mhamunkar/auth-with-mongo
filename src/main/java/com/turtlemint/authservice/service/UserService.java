package com.turtlemint.authservice.service;

import com.turtlemint.authservice.entity.UserRoleMapping;
import com.turtlemint.authservice.model.PermissionModel;
import com.turtlemint.authservice.model.RoleModel;
import com.turtlemint.authservice.model.UserModel;
import com.turtlemint.authservice.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @Autowired
    private RolePermissionMappingRepository rolePermissionMappingRepository;

    public Mono<UserModel> findUserById(String id){
        log.info("Finding user by id: {}", id);
        AtomicReference<UserModel> userModel = new AtomicReference<>();
        //return Mono.error for id not found or id is not valid
        return userRepository.findById(new ObjectId(id))
                //.defaultIfEmpty(User.builder().id(new ObjectId(id)).build())
                .log()
                .flatMap(user -> {log.info("User: {}", user); userModel.set(UserModel.from(user)); return Mono.just(user);})
                .flatMapMany(user -> userRoleMappingRepository.findByUserId(user.getId()))
                .map(UserRoleMapping ::getRoleId)
                .collectList()
                .flatMapMany(roleIds -> findByRoleIds(roleIds))
                .collectList()
                .map(roleModels -> {userModel.get().setRoles(roleModels); return roleModels;})
                .doOnNext(roleModels -> log.info("UserModel: {}", userModel.get()))
                .log()
                .flatMap(roleModels -> Mono.just(userModel.get()))
                //.thenReturn(userModel.get())
        ;
    }

    //TODO: MOVE THIS TO RoleService
    public Flux<RoleModel> findByRoleIds(List<ObjectId> roleIds){
        final Map<ObjectId, RoleModel> idToRoleModels = new ConcurrentHashMap<>();
        final Map<ObjectId, ObjectId> permissionIdToRoleIdMap = new ConcurrentHashMap<>();

        return roleRepository.findAllById(roleIds)
                .doOnNext(role -> idToRoleModels.put(role.getId(), RoleModel.from(role)))
                .map(role -> role.getId())
                //.flatMap(objectId -> rolePermissionMappingRepository.findByRoleId(objectId))
                .collectList()
                .flatMapMany(roleObjectIds -> rolePermissionMappingRepository.findByRoleIds(roleObjectIds))

                .doOnNext(rolePermissionMapping -> {
                    ObjectId roleId = rolePermissionMapping.getRoleId();
                    ObjectId permissionId = rolePermissionMapping.getPermissionId();
                    permissionIdToRoleIdMap.put(permissionId, roleId);
                })
                .map(rolePermissionMapping -> rolePermissionMapping.getPermissionId())
                .collectList()
                .flatMapMany(permissionIds -> permissionRepository.findAllById(permissionIds))
                .doOnNext(permission -> {
                    RoleModel roleModel = idToRoleModels.get(permissionIdToRoleIdMap.get(permission.getId()));
                    roleModel.addPermission(PermissionModel.from(permission));
                })
                .thenMany(Flux.fromIterable(idToRoleModels.values()));
    }



}
