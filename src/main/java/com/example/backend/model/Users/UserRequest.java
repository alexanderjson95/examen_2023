package com.example.backend.model.Users;

import com.example.backend.ToExport;
import com.example.backend.model.roles.Role;
import com.example.backend.model.roles.RoleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@ToExport
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String publicKey;
    private List<RoleRequest> roles;
}
