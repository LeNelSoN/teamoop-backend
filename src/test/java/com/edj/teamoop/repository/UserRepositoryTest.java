package com.edj.teamoop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.edj.teamoop.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;


    public void shouldSaveUser() {
        User user = new User();

        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("test");

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getCreatedAt()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Test");
        assertThat(savedUser.getEmail()).isEqualTo("test@gmail.com");
        assertThat(savedUser.getPassword()).isEqualTo("test");
    }
}
