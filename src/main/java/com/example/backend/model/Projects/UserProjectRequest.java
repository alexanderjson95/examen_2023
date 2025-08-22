package com.example.backend.model.Projects;

import com.example.backend.model.Users.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * DTO för projekt
 */
@Getter
@Setter
public class UserProjectRequest {
    @Size(max = 15)
    private String role;
    private Boolean isAdmin;
    private Boolean joined;
}
