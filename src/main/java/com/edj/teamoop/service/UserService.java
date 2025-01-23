package com.edj.teamoop.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.exception.EmailAlreadyExistsException;
import com.edj.teamoop.exception.InvalidPasswordException;
import com.edj.teamoop.model.User;
import com.edj.teamoop.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,255}$";

    public void createUser(UserDTO userDTO) {
        
        if (userDTO.getName() == null || userDTO.getEmail() == null || userDTO.getPassword() == null) {
            throw new IllegalArgumentException("Les champs nom, email et mot de passe sont obligatoires.");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("L'email " + userDTO.getEmail() + " est déjà utilisé.");
        }

        if (!Pattern.matches(PASSWORD_REGEX, userDTO.getPassword())) {
            throw new InvalidPasswordException("Le mot de passe doit contenir au moins 12 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.");
        }

        User user = new User();
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }
}
