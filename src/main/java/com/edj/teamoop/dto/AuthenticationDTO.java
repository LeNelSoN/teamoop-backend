package com.edj.teamoop.dto;

public record AuthenticationDTO(String email, String password) {
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
