package com.example.backend;


import com.example.backend.model.Users.UserRequest;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.JwtService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final UserService service;
    private final AuthenticationManager authenticationManager;

    public void Authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e){
            throw new Exception("Invalid username or password");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createJwTToken(@RequestBody AuthRequest req) throws Exception {
        Authenticate(req.getUsername(),req.getPassword());
        UserDetails u = service.findUserByUsername(req.getUsername());
        String token = jwtService.generateToken(u);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody AuthRequest req) throws Exception {
        UserDetails u = service.findUserByUsername(req.getUsername());
        String refreshedToken = jwtService.generateToken(u);
        return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
    }}
