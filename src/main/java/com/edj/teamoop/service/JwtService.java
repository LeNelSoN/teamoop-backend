package com.edj.teamoop.service;

import com.edj.teamoop.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final UserService userService;
    private final String encryptionKey;

    @Autowired
    public JwtService(UserService userService, @Value("${spring.encryption.key}") String encryptionKey) {
        this.userService = userService;
        this.encryptionKey = encryptionKey;
    }

    public Map<String, String> generate(String username) {

        User user = (User) this.userService.loadUserByUsername(username);

        return this.generateJwt(user);
    }

    private Map<String, String> generateJwt(User user) {

        Map<String, Object> claims = Map.of(
                "email", user.getEmail(),
                "name", user.getName()
        );

        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

         String bearer = Jwts.builder()
             .issuedAt(new Date(currentTime))
             .expiration(new Date(expirationTime))
             .subject(user.getEmail())
             .claims(claims)
             .signWith(getKey())
             .compact();

        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(encryptionKey.getBytes(StandardCharsets.UTF_8));
    }
}
