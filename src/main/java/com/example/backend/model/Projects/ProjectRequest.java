package com.example.backend.model.Projects;

import com.example.backend.model.Users.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


/**
 * DTO för projekt
 */
public class ProjectRequest {
    @NotNull(message = "Projekt saknas")
    private long projectId;
    @NotNull(message = "Ogiltlig användare")
    private long userId;
}
