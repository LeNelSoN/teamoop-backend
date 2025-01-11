package com.edj.teamoop.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping(path = "/api/users", consumes = APPLICATION_JSON_VALUE)
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUser(@RequestBody UserDTO userDTO) {
        
        this.userService.createUser(userDTO);
    }
}
