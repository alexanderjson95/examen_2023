package com.example.backend.model.Users;

import com.example.backend.ToExport;
import com.example.backend.model.roles.RoleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@ToExport
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestPatch {
    private String firstName;
    private String lastName;
    private String password;
    private List<RoleRequest> roles;
}
