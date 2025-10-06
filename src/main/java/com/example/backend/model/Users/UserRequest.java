package com.example.backend.model.Users;

import com.example.backend.ToExport;
import com.example.backend.model.roles.Role;
import com.example.backend.model.roles.RoleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Användarnamn får inte vara tomt")
    @Size(min = 5, max=15, message = "Användarnamn måste vara mellan 5 - 15 tecken" )
    private String username;

    @NotBlank(message = "Förnamn får inte vara tomt")
    private String firstName;

    @NotBlank(message = "Efternamn får inte vara tomt")
    private String lastName;

    @NotBlank(message = "Lösenordet får inte vara tomt")
    @Size(min = 7, message = "Lösenordet måste vara minst 7 tecken")
    private String password;

    @NotBlank(message = "Email får inte vara tomt")
    @Size(min = 5, max=15, message = "Email måste vara mellan 5 - 15 tecken" )
    private String email;


    private String publicKey;


    private List<RoleRequest> roles;
}
