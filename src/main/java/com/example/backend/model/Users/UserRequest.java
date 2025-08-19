package com.example.backend.model.Users;

import jakarta.validation.constraints.NotNull;

public class UserRequest {
    @NotNull(message = "Användarid ogiltligt")
    private Long userId;
}
