package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Användarnamn får inte vara tomt")
    @Size(min = 5, max=15, message = "Användarnamn måste vara mellan 5 - 15 tecken" )
    private String username;

    @NotBlank(message = "Lösenordet får inte vara tomt")
    @Size(min = 7, message = "Lösenordet måste vara minst 7 tecken")
    private String password;


    //@NotBlank - utkommenterat under dev
    @Email
    private String email;


    public Users(String alexander, String alexander123, String mail) {
    }
}
