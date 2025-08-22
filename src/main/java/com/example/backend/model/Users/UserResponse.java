package com.example.backend.model.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String publicKey;
    public static UserResponse returnUser(Users users) {
        return new UserResponse(
                users.getId(),
                users.getUsername(),
                users.getEmail(),
                users.getPublicKey()
        );
    }


}
