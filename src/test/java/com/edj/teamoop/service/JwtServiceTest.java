package com.edj.teamoop.service;

import com.edj.teamoop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    private String encryptionKey = "5fd4d6432f2545ceb0078a3a992f31537c7c0fe65d31539d1bf403fb7bd0c596"; // Clé de test

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(userService, encryptionKey);
    }

    @Test
    void testGenerate_ShouldReturnJwt() {

        String username = "test@example.com";
        User mockUser = new User();
        mockUser.setName("Test");
        mockUser.setEmail(username);

        when(userService.loadUserByUsername(username)).thenReturn(mockUser);

        Map<String, String> result = jwtService.generate(username);

        assertNotNull(result);
        assertTrue(result.containsKey("bearer"));
        assertNotNull(result.get("bearer"));
        assertFalse(result.get("bearer").isEmpty());
    }
}
