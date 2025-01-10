package com.edj.teamoop.service;

import com.edj.teamoop.model.User;
import com.edj.teamoop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("test123");

        when(userRepository.save(user)).thenReturn(user);

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
    }
}
