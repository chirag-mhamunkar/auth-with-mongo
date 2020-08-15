package com.turtlemint.authservice.controller;

import com.turtlemint.authservice.model.UserModel;
import com.turtlemint.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Mono<UserModel> fetchUserById(@PathVariable("id") String id){
        log.info("Request received to fetch an user by id: {}", id);
        return userService.findUserById(id);
    }
}
