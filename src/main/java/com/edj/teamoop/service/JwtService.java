package com.edj.teamoop.service;

import com.edj.teamoop.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Service
public class JwtService {

    @Autowired
    private UserService userService;

    public Map<String, String> generate(String username) {

        User user = (User) this.userService.loadUserByUsername(username);

        return this.generateJwt(user);
    }

    private Map<String, String> generateJwt(User user) {

        Map<String, String> claims = Map.of(
                "email", user.getEmail(),
                "name", user.getName()
        );

        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

         String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of("bearer", bearer);
    }

    private Key getKey() {

        final String ENCRYPTION_KEY = "f63c2c3e88bbd9c7d18666299249135eac8ae93f4793a134e60ff458da96d0c0";
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);

        return Keys.hmacShaKeyFor(decoder);
    }
}
