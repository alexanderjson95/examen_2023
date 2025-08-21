package com.example.backend;


import com.example.backend.model.Users.UserRequest;
import com.example.backend.model.Users.UserResponse;
import com.example.backend.model.Users.Users;
import com.example.backend.service.JwtService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest req) throws NoSuchAlgorithmException {
        service.addUser(
                req.getUsername(),
                req.getPassword(),
                req.getEmail(),
                req.getPublicKey()
        );
        Users createdUser = service.findUserByUsername(req.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.returnUser(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users>  getUserById(@PathVariable Long id){
        return ResponseEntity.ok(service.findUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        service.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse>  updateUser(@PathVariable Long id, @RequestBody UserRequest req) throws NoSuchAlgorithmException {
        Users updatedUser = service.updateUser(
                id,
                req.getUsername(),
                req.getPassword(),
                req.getEmail(),
                req.getPublicKey()
        );
        return ResponseEntity.ok(UserResponse.returnUser(updatedUser));
    }




}
