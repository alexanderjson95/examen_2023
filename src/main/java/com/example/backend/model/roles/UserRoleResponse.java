package com.example.backend.model.roles;


import com.example.backend.ToExport;
import com.example.backend.model.Projects.Project;
import com.example.backend.model.Users.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToExport
@Getter
@Setter
@Builder
public class UserRoleResponse {
    private Long userId;
    private String roleType;
    private String firstName;
    private String lastName;

    public static UserRoleResponse fromUserRole(UserRole ur) {
        return  UserRoleResponse.builder()
                .userId(ur.getUser().getId())
                .roleType(String.valueOf(ur.getRole().getRole()))
                .firstName(ur.getUser().getFirstName())
                .lastName(ur.getUser().getLastName())
                .build();
    }
}
