package com.example.backend.model.roles;


import com.example.backend.ToExport;
import com.example.backend.model.Users.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@ToExport

@Getter
@Setter
@Builder
public class RoleRequest {
    private Long id;
    @JsonProperty("role")

    private RoleType roleType;
    public static RoleRequest fromRole(Role role) {
        return new RoleRequest(
                role.getId(),
                role.getRole()
        );
    }
}
