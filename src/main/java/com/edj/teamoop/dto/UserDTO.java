package com.edj.teamoop.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class UserDTO {
    
    private Long id;
    private String name;
    private String email;

    @JsonProperty
    private String password;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Long numberOfUnreadNotifications;
}
