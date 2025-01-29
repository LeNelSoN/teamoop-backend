package com.edj.teamoop.controller;

import com.edj.teamoop.dto.AuthentifcationDTO;
import com.edj.teamoop.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;


@RestController
@RequestMapping(path = "/api/users", consumes = APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        this.userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created !");
    }

    @PostMapping(path = "/login")
    public Map<String, String> login(@RequestBody AuthentifcationDTO authentifcationDTO) {

        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentifcationDTO.email(), authentifcationDTO.password())
            );

            return jwtService.generate(authentifcationDTO.email());

        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials")).getBody();
        }
    }
}
