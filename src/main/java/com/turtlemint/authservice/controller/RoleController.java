package com.turtlemint.authservice.controller;


import com.turtlemint.authservice.entity.Role;
import com.turtlemint.authservice.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public Flux<Role> fetchAllRoles(){
        log.info("Request received to fetch all roles.");
        return roleRepository.findAll();
    }
}
