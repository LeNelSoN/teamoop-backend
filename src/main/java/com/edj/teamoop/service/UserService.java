package com.edj.teamoop.service;

import java.util.List;
import java.util.regex.Pattern;

import com.edj.teamoop.exception.DataNotFoundException;
import com.edj.teamoop.mapper.UserMapper;
import com.edj.teamoop.model.Notification.Notification;
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
    public final UserMapper userMapper;

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

    public User createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists !");
        }

        if (!Pattern.compile(PASSWORD_REGEX).matcher(userDTO.getPassword()).matches()) {
            throw new InvalidPasswordException("Password must contain at least 12 characters, one uppercase, one lowercase, one number and one special character !");
        }

        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(user);
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found !"));

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

    public void save(User user) {
        userRepository.save(user);
    }
}