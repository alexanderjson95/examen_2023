package com.example.backend;

import com.example.backend.model.Users.RegisterRequest;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.UserMessageRepository;
import com.example.backend.repository.UserProjectRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.CryptoService;
import com.example.backend.service.ProjectService;
import com.example.backend.service.UserMessageService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc

public class EndToEndTest {


    /*
     * Skapar en sql databas i en container
     */

    @Container
    @ServiceConnection
    static MySQLContainer<?> sql = new MySQLContainer<>(
            DockerImageName.parse("mysql:8.1")
    )
            .withDatabaseName("db")
            .withUsername("u")
            .withPassword("p");


    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserMessageService msgService;

    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private UserMessageRepository userMessagesRepo;

    String username = "Alexander";
    String password = "Alexander123";
    String email = "alexander123@mail.com";
    String publicKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEQGr5sZ0R0x2Xv9QeZszv9cG3WgA3dJmCw2xK0lGrg0Y0km6h8AlxV2hlYn3V6ug5pKbmI7GLTfKqkEThj9cK9A==";

    String username2 = "2Alexander";
    String password2 = "2Alexander123";
    String email2 = "2alexander123@mail.com";
    String publicKey2 = "2MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEQGr5sZ0R0x2Xv9QeZszv9cG3WgA3dJmCw2xK0lGrg0Y0km6h8AlxV2hlYn3V6ug5pKbmI7GLTfKqkEThj9cK9A==";


    // SKApA ANDRA
    @Autowired
    private CryptoService crypto;
    @Autowired
    private TestRestTemplate restTemplate;



    @Test
    void EndToEnd(){
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("login", "/authenticate");
        endpoints.put("refresh", "/refresh");

        endpoints.put("refresh", "/backend/register");
        endpoints.put("login", "/authenticate");
        endpoints.put("login", "/authenticate");
        endpoints.put("login", "/backend/authenticate");


        RegisterRequest regReq = new RegisterRequest();
        regReq.setUsername(username);
        regReq.setUsername(email);
        regReq.setUsername(password);
        regReq.setUsername(publicKey);
        regReq.setUsername(username);

        ResponseEntity<String> registerResponse =
                restTemplate.postForEntity("")
    }


}
