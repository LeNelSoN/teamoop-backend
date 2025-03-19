package com.edj.teamoop.controller;

import com.edj.teamoop.dto.AuthenticationDTO;
import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.model.User;
import com.edj.teamoop.service.JwtService;
import com.edj.teamoop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api", consumes = APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        userService.save(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationDTO authenticationDTO) {
        UserDTO user = userService.findByEmail(authenticationDTO.getEmail());
        if (user == null || !passwordEncoder.matches(authenticationDTO.getPassword(), user.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid credentials");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        String token = jwtService.generateToken(user);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
