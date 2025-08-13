package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

}
