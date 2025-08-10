package com.example.backend;

import com.example.backend.model.Users;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class UsersRepoTest {


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
    private UserRepository uRep;


    // Testar GET och Create
    @Test
    public void shouldCreateAndGetUser() {
        Users users = new Users(0,"alex", "alex123");
        uRep.save(users);

        Users foundU = uRep.findByUsername(users.getUsername());
        System.out.println("user: " + foundU.getUsername() + "password: " + foundU.getPassword());
        Assertions.assertEquals("alex", foundU.getUsername());
    }

    @Test
    public void shouldRemoveUser() {

    }

}

