package com.edj.teamoop.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edj.teamoop.model.User;
import com.edj.teamoop.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping(path = "/api/users", consumes = APPLICATION_JSON_VALUE)
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    public void addUser(@RequestBody User user) {
        System.out.println("Utilisateur reçu : " + user.getName());
    }
}
