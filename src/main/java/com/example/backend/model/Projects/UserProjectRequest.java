package com.example.backend.model.Projects;

import com.example.backend.ToExport;
import com.example.backend.model.Users.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * DTO för userproject - denna klass används endast för att hantera den användardata som är relevant inom ett projekt och endast inom
 * ett projekt. En blueprint för en användare inom ett projekt.
 */
@ToExport
@Getter
@Setter
public class UserProjectRequest {

    @Size(max = 15)
    private String role;
    private boolean isAdmin;
    private boolean hasJoined;
    private RequestType requestType;
}
