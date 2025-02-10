package com.edj.teamoop.service;

import java.util.List;
import java.util.regex.Pattern;

import com.edj.teamoop.mapper.UserMapper;
import com.edj.teamoop.model.Notification.Notification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.exception.EmailAlreadyExistsException;
import com.edj.teamoop.exception.InvalidPasswordException;
import com.edj.teamoop.model.User;
import com.edj.teamoop.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found !"));
    }

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,255}$";

    public void createUser(UserDTO userDTO) {
        
        if (userDTO.getName() == null || userDTO.getEmail() == null || userDTO.getPassword() == null) {
            throw new IllegalArgumentException("The name, email and password fields are required.");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + userDTO.getEmail() + " already used !");
        }

        if (!Pattern.matches(PASSWORD_REGEX, userDTO.getPassword())) {
            throw new InvalidPasswordException("The password must contain at least 12 characters, an uppercase letter, a lowercase letter, a number and a special character.");
        }

        User user = new User();
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found !"));

        UserDTO userDTO = userMapper.toDTO(user);

        Long numberOfUnreadNotification = countNumberOfUnreadNotification(user.getNotifications());

        userDTO.setNumberOfUnreadNotifications(numberOfUnreadNotification);

        return userDTO;
    }

    private Long countNumberOfUnreadNotification(List<Notification> notificationList) {
        return notificationList.stream()
                .filter((notification -> !notification.isRead()))
                .count();
    }
}