package com.edj.teamoop.service;

import com.edj.teamoop.dto.user.CreateUserDTO;
import com.edj.teamoop.dto.user.UserDTO;
import com.edj.teamoop.exception.DataNotFoundException;
import com.edj.teamoop.exception.EmailAlreadyExistsException;
import com.edj.teamoop.exception.InvalidPasswordException;
import com.edj.teamoop.mapper.UserMapper;
import com.edj.teamoop.model.Notification.MessageNotification;
import com.edj.teamoop.model.Notification.Notification;
import com.edj.teamoop.model.User;
import com.edj.teamoop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceTest jwtService;

    CreateUserDTO userDTO = new CreateUserDTO("Test","test@gmail.com","Test123!45678");

    @Test
    void testCreateUser_OK() {
        when(userRepository.existsByEmail(userDTO.email())).thenReturn(false);
        when(passwordEncoder.encode(userDTO.password())).thenReturn("HashedPassword123!");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        userService.createUser(userDTO);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("Test", capturedUser.getName());
        assertEquals("test@gmail.com", capturedUser.getEmail());
        assertEquals("HashedPassword123!", capturedUser.getPassword());

        verify(passwordEncoder, times(1)).encode(userDTO.password());
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(userDTO.email())).thenReturn(true);

        Exception exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("Email test@gmail.com already used !", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_InvalidPassword() {
        CreateUserDTO invalidUserDTO = new CreateUserDTO("Test","test@gmail.com","test123");

        Exception exception = assertThrows(InvalidPasswordException.class, () -> {
            userService.createUser(invalidUserDTO);
        });

        assertEquals("The password must contain at least 12 characters, an uppercase letter, a lowercase letter, a number and a special character.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_MissingFields() {
        CreateUserDTO missingFieldUserDTO = new CreateUserDTO("Test",null,"Test123!45678");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(missingFieldUserDTO);
        });

        assertEquals("The name, email and password fields are required.", exception.getMessage());
    }

    @Test
    void testFindByEmail_OK() {

        MessageNotification notification1 = new MessageNotification();
        notification1.setMessage("message1");

        MessageNotification notification2 = new MessageNotification();
        notification2.setMessage("message2");

        List<Notification> notificationList = List.of(notification1, notification2);

        User user = new User();
        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("Test123!45678");
        user.setNotifications(notificationList);

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("Test123!45678");
        userDTO.setNumberOfUnreadNotifications(2L);

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO userDTO1 = userService.findByEmail("test@gmail.com");

        assertEquals("Test", userDTO.getName());
        assertEquals("test@gmail.com", userDTO.getEmail());
        assertEquals("Test123!45678", userDTO.getPassword());
        assertEquals(2L, userDTO1.getNumberOfUnreadNotifications());
        verify(userRepository, times(1)).findByEmail("test@gmail.com");
    }

    @Test
    void testFindByEmail_UserNotFound() {

        when(userRepository.findByEmail("test@gmail.com")).thenThrow(new DataNotFoundException("User not found !"));

        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            userService.findByEmail("test@gmail.com");
        });
        assertEquals("User not found !", exception.getMessage());
    }


}
