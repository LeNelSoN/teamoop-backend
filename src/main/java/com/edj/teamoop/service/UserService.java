package com.edj.teamoop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.model.User;
import com.edj.teamoop.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(UserDTO userDTO) {
            
        if (userDTO.getName() == null || userDTO.getEmail() == null || userDTO.getPassword() == null) {
            throw new IllegalArgumentException("Les champs nom, email et mot de passe sont obligatoires.");
        }
        
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("L'adresse mail est déja utilisé !");
        }
        
        User user = new User();

        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(hashedPassword);

        this.userRepository.save(user);

    }
}
