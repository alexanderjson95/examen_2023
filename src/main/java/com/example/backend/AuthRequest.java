package com.example.backend;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @NotBlank(message = "Användarnamn saknas")
    private String username;
    @NotBlank(message = "Lösenord saknas")
    private String password;
}
