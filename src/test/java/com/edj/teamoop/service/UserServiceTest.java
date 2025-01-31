package com.edj.teamoop.service;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.exception.EmailAlreadyExistsException;
import com.edj.teamoop.exception.InvalidPasswordException;
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
    void testCreateUser_OK() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("Test123!45678");

        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("HashedPassword123!");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        userService.createUser(userDTO);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("Test", capturedUser.getName());
        assertEquals("test@gmail.com", capturedUser.getEmail());
        assertEquals("HashedPassword123!", capturedUser.getPassword());

        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
    }

    @Test
    void testCreateUser_EmailAlreadyExists_OK() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("Test123!45678");

        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        Exception exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("Email test@gmail.com already used !", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_InvalidPassword_OK() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("test123");

        Exception exception = assertThrows(InvalidPasswordException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("The password must contain at least 12 characters, an uppercase letter, a lowercase letter, a number and a special character.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_MissingFields_OK() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail(null); 
        userDTO.setPassword("Test123!45678");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("The name, email and password fields are required.", exception.getMessage());
    }
}
