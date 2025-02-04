package com.edj.teamoop.service;

import com.edj.teamoop.dto.UserDTO;
import com.edj.teamoop.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final UserService userService;
    private final String encryptionKey;

    @Autowired
    public JwtService(UserService userService, @Value("${encryption.key}") String encryptionKey) {
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
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .addClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of("bearer", bearer);
    }

    private Key getKey() {

        final byte[] decoder = Decoders.BASE64.decode(encryptionKey);

        return Keys.hmacShaKeyFor(decoder);
    }
}
