package com.example.backend;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProjectTest {


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
    private ProjectRepository projectService;

    @Autowired
    private ProjectRepository projectRepo;


    @BeforeEach
    void cleanDB(){
        projectRepo.deleteAll();
    }



    @Test
    void shouldCreateAProject_success(){

    }

    @Test
    void shouldCreateAProject_fail(){

    }



    @Test
    void shouldRemoveAProject_success()  {}

    @Test
    void shouldRemoveAProject_fail()  {}




}

