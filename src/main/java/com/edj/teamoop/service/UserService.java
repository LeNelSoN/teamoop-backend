package com.edj.teamoop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edj.teamoop.model.User;
import com.edj.teamoop.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public void createUser(User user) {

        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Les champs name, email et password sont obligatoires.");
        }

        this.userRepository.save(user);
    }
}
