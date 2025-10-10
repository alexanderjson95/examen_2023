package com.example.backend;


import com.example.backend.model.Users.UserRequest;
import com.example.backend.model.Users.UserRequestPatch;
import com.example.backend.model.Users.UserResponse;
import com.example.backend.model.Users.Users;
import com.example.backend.model.roles.RoleResponse;
import com.example.backend.model.roles.UserRoleResponse;
import com.example.backend.service.UserService;
import jakarta.validation.Valid;
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
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        service.removeUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest req) throws NoSuchAlgorithmException {
        System.out.println("DEBUG Request: " + req.getFirstName());
        service.addUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoleTypes(){
        return ResponseEntity.ok(service.findAllRoles());
    }


    @GetMapping("/{userId}/roles")
    public ResponseEntity<List<String>> getUserRoles(@PathVariable Long userId) {
        List<String> roles = service.getRoleNamesByUserId(userId);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/role")
    public ResponseEntity<List<String>> getMyRoles(Principal principal) {
        Long userId = service.findUserByUsername(principal.getName()).getId();
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
        Users user = service.findUserByUsername(username);
        return ResponseEntity.ok(UserResponse.returnUser(user));
    }





    @PatchMapping("/update")
    public ResponseEntity<Void> updateUser(Principal principal, @RequestBody UserRequestPatch req) throws NoSuchAlgorithmException {
        String username = principal.getName();
        Users user = service.findUserByUsername(username);
        service.updateUser(user,req);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
