package com.example.backend.model.roles;


import com.example.backend.ToExport;
import com.example.backend.model.Projects.Project;
import com.example.backend.model.Users.RoleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@ToExport
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RoleType roleType;
    public static RoleResponse fromRole(Role role) {
        return new RoleResponse(
                role.getRole()
        );
    }
}
