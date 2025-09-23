package com.example.backend.service;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.Exceptions.ResourceNotFoundException;
import com.example.backend.model.Users.*;
import com.example.backend.model.roles.*;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserRoleRepository;
import com.example.backend.security.configs.KeyConfig;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder enc;

    @Autowired
    UserRoleRepository userRoleRepo;

    @Autowired
    RoleRepository roleRepo;



    private final CryptoService crypto;
    private final KeyConfig keyConfig;


    @Autowired
    public UserService(CryptoService crypto, KeyConfig keyConfig) {
        this.crypto = crypto;
        this.keyConfig = keyConfig;
    }

    // Hämtar lista på alla user roles
    public List<UserRoleResponse> findAllUsersWithRoles() {
        return userRoleRepo.findAll().stream()
                .map(role ->  UserRoleResponse.builder()
                        .userId(role.getUser().getId())
                        .roleType(role.getRole().getRole().name())
                        .firstName(role.getUser().getFirstName())
                        .lastName(role.getUser().getLastName())
                        .build()).toList();
    }

    // Hämtar lista på enums
    public List<RoleResponse> findAllRoles() {
        return roleRepo.findAll().stream()
                .map(role ->  RoleResponse.builder()
                        .roleType(role.getRole())
                        .build()).toList();
    }
//
//    public List<UserRoleResponse> findUserRoles(Long userId) {
//        return userRoleRepo.findRoleNamesByUserId(userId)
//                .stream()
//                .map(UserRoleResponse::fromUserRole)
//                .collect(Collectors.toList());
//    }

    public List<String> getRoleNamesByUserId(Long userId) {
        List<String> roles = userRoleRepo.findRoleNamesByUserId(userId);
        System.out.println("Fetched roles for user " + userId + ": " + roles);
        return roles;
    }


    // hämtar userid, roleid och yoe(om inte null)
    public List<UserRoleResponse> findAllUserRoles() {
        return userRoleRepo.findAll()
                .stream()
                .map(UserRoleResponse::fromUserRole)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest req) throws NoSuchAlgorithmException {
        if(userRepo.findByUsername(req.getUsername()).isPresent()){
            throw new DataTakenException("Användarnamnet är taget");
        };
        String encodedPassword = enc.encode(req.getPassword());
        SecretKey aesKey = keyConfig.secretKey();
        String encodedKey = crypto.encodeBase64_secretKey(aesKey);
        Users nUser = new Users();
        nUser.setUsername(req.getUsername());
        nUser.setFirstName(req.getFirstName());
        nUser.setLastName(req.getLastName());
        nUser.setPassword(encodedPassword);
        nUser.setEmail(req.getEmail());
        nUser.setPublicKey(req.getPublicKey());
        nUser.setSecretKey(encodedKey);
        userRepo.save(nUser);

        for(RoleRequest roleReq : req.getRoles()) {
            Role role = roleRepo.findByRole(roleReq.getRoleType())
                    .orElseThrow(() -> new ResourceNotFoundException("Rollen finns inte: " + roleReq.getRoleType()));
            UserRole userRole = new UserRole();
            userRole.setUser(nUser);
            userRole.setRole(role);
            userRoleRepo.save(userRole);
        }
    }

    @Transactional
    public List<UserResponse> findAllUsers() {
        return userRepo.findAll().stream()
                .map(user ->  UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build()).toList();
    }

    /**
     * Packar up optional med null check
     * @param id  user id
     * @return Users objekt
     */
    public Users findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Användaren kunde inte hittas.")); // Kollar om null, skickar  ut "NoSuchElementException" om det är, annars returnerar värdet
    };

    /**
     * @param username
     * @return Users objekt
     */
    public Users findUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Användaren kunde inte hittas.")); // Kollar om null, skickar  ut "NoSuchElementException" om det är, annars returnerar värdet
    };


    public List<UserResponse> findUsersByQuery(String query, String value){
        List<Users> users;
        switch (query.toLowerCase()) {
            case "username" -> users = userRepo.findAllByUsernameContainingIgnoreCase(value);

            default -> throw new IllegalArgumentException("Sökordet finns inte!" + query);
        }

        return users.stream().map(user ->  UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build()).toList();
    }


    public void removeUser(Long id) {
        Users foundUser = findUserById(id);
        userRepo.delete(foundUser);
    }
    public Users updateUser(Long id, String username, String password, String email, String publicKey) throws NoSuchAlgorithmException {
        Users existing = findUserById(id);
        existing.setUsername(username);
        existing.setPassword(enc.encode(password));
        existing.setEmail(email);
        existing.setPublicKey(publicKey);
        return userRepo.save(existing);
    }




}
