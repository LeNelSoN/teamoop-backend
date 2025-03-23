package com.edj.teamoop.dto.user;

import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotNull(message = "Name is required") String name,
        @NotNull(message = "Email is required") String email,
        @NotNull(message = "Password is required") String password
        ) {

}
