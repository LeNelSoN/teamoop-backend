package com.edj.teamoop.controller;

import com.edj.teamoop.dto.AuthenticationDTO;
import com.edj.teamoop.model.User;
import com.edj.teamoop.service.JwtService;
import com.edj.teamoop.service.JwtServiceTest;
import com.edj.teamoop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_shouldReturnTokenWhenCredentialsValid() {

        AuthenticationDTO authenticationDTO = new AuthenticationDTO("test@example.com", "password123");
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword");

        when(userService.findByEmail("test@example.com")).thenReturn(mockUser);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtService.generate("test@example.com")).thenReturn(Map.of("bearer", "jwtToken123"));

        ResponseEntity<Map<String, String>> response = authController.login(authenticationDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("bearer");
        assertThat(Objects.requireNonNull(response.getBody()).get("bearer")).isEqualTo("jwtToken123");
    }

    @Test
    void login_shouldReturnUnauthorized_WhenInvalidsCredentials() {

        AuthenticationDTO authenticationDTO = new AuthenticationDTO("wrong@example.com", "wrongPassword");

        when(userService.findByEmail("wrong@example.com")).thenReturn(null);

        ResponseEntity<Map<String, String>> response = authController.login(authenticationDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).containsKey("message");
        assertThat(response.getBody().get("message")).isEqualTo("Invalid credentials");

    }
}
