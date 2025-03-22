package com.edj.teamoop.controller;

import com.edj.teamoop.mapper.UserMapper;
import com.edj.teamoop.service.JwtService;
import com.edj.teamoop.service.NotificationService;
import com.edj.teamoop.utility.SecurityContextUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        this.userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created !");
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserDTO> getUserInfo() {
        return ResponseEntity.ok(userService.findByEmail(SecurityContextUtil.getUserPrincipalName()));
    }
}
