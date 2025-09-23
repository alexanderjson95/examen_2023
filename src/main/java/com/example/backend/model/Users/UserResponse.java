package com.example.backend.model.Users;

import com.example.backend.model.roles.RoleRequest;
import com.example.backend.model.roles.UserRoleResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String publicKey;
    private List<UserRoleResponse> roles;



    public static UserResponse returnUser(Users users) {
        return new UserResponse(
                users.getId(),
                users.getEmail(),
                users.getUsername(),
                users.getFirstName(),
                users.getLastName(),
                users.getPublicKey(),
                users.getUserRoles().stream().map(UserRoleResponse::fromUserRole).collect(Collectors.toList())
        );
    }

}
