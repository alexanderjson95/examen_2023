package com.example.backend;


import com.example.backend.model.Users.UserRequest;
import com.example.backend.model.Users.UserResponse;
import com.example.backend.model.Users.Users;
import com.example.backend.model.roles.RoleResponse;
import com.example.backend.model.roles.UserRoleResponse;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@ToExport("controller")
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest req) throws NoSuchAlgorithmException {
        System.out.println("DEBUG Request: " + req.getFirstName());
        service.addUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoleTypes(){
        System.out.println("wefjewpoiwjpifjpeifij");
        return ResponseEntity.ok(service.findAllRoles());
    }
//
//    @GetMapping("/roles/{userId}")
//    public ResponseEntity<List<UserRoleResponse>> getUserRole(@PathVariable Long userId){
//        return ResponseEntity.ok(service.findUserRoles(userId));
//    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<List<String>> getUserRoles(@PathVariable Long userId) {
        List<String> roles = service.getRoleNamesByUserId(userId);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/userroles")
    public ResponseEntity<List<UserRoleResponse>> getAllUserRoles() {
        List<UserRoleResponse> roles = service.findAllUsersWithRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = service.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(
            @RequestParam String query,
            @RequestParam String value
    ) {
        return ResponseEntity.ok(service.findUsersByQuery(query, value));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Users>  getUserById(@PathVariable Long id){
        return ResponseEntity.ok(service.findUserById(id));
    }


    @GetMapping("/returnUser")
    public ResponseEntity<UserResponse> returnUser(Principal principal){
        String username = principal.getName();
        Users user = service.findUserByUsername(username);System.out.println("wefjewpoiwjpifjpeifij");
        return ResponseEntity.ok(UserResponse.returnUser(user));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        service.removeUser(id);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest req) throws NoSuchAlgorithmException {
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
