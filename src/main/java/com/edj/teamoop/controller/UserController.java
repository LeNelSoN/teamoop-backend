package com.edj.teamoop.controller;

import com.edj.teamoop.dto.AuthenticationDTO;
import com.edj.teamoop.mapper.UserMapper;
import com.edj.teamoop.service.JwtService;
import com.edj.teamoop.service.NotificationService;
import com.edj.teamoop.utility.SecurityContextUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.service.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;


@RestController
@RequestMapping(path = "/api/user", consumes = APPLICATION_JSON_VALUE)
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

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationDTO authenticationDTO) {

        var user = userService.findByEmail(authenticationDTO.email());
        if (user == null || !passwordEncoder.matches(authenticationDTO.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }

        Map<String, String> token = jwtService.generate(authenticationDTO.email());
        return ResponseEntity.ok(token);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserDTO> getUserInfo() {
        return ResponseEntity.ok(userService.findByEmail(SecurityContextUtil.getUserPrincipal()));
    }
}
