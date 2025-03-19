package com.edj.teamoop.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private long numberOfUnreadNotifications;
}
