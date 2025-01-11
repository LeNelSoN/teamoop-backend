package com.edj.teamoop.service;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.model.User;
import com.edj.teamoop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("test123");

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("hashedPassword123");

        User savedUser = new User();
        savedUser.setName(userDTO.getName());
        savedUser.setEmail(userDTO.getEmail());
        savedUser.setPassword("hashedPassword123");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.createUser(userDTO);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("Test", capturedUser.getName());
        assertEquals("test@gmail.com", capturedUser.getEmail());
        assertEquals("hashedPassword123", capturedUser.getPassword());

        verify(passwordEncoder).encode(userDTO.getPassword());
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("test123");

        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("L'adresse mail est déja utilisé !", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
