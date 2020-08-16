package com.turtlemint.authservice.controller;

import com.turtlemint.authservice.entity.Permission;
import com.turtlemint.authservice.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionRepository permissionRepository;

    @GetMapping
    public Flux<Permission> fetchAllPermissions(){
        log.info("Request received to fetch all permissions");
        return permissionRepository.findAll();
    }
}
