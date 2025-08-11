package com.example.backend;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.model.Users;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
    private UserService userService;

    @Autowired
    private UserRepository userRepo;


    /**
     * Test: Vi testar att skapa en användare. Vi kollar sedan att det finns i databasen.
     * @see #throwsUsernameTakenExceptionWhenUsernameExists_register() för scenario där användare redan finns
     */
    @Test
        void shouldCreateAndGetUser_register() {
        Users users = new Users(0,"Alexander", "Alexander123");
        userService.addUser(users);

        Optional<Users> foundU = userRepo.findByUsername(users.getUsername());
        Assertions.assertTrue(foundU.isPresent());
    }

    /**
     * Test: Användaren försöker registrera sig med taget användarnamn.
      * {@link DataTakenException} ska kastas med rätt meddelande*
     */
        @Test
        void throwsUsernameTakenExceptionWhenUsernameExists_register() {
        Users newUser = new Users(0, "Alexander", "Alexander123");
        userService.addUser(newUser);
        DataTakenException e = Assertions.assertThrows(DataTakenException.class,
                () -> userService.addUser(newUser));
        Assertions.assertEquals("Användarnamnet är taget", e.getMessage());
    }

    /**
     * Test: Ifall användarnamn / eller lösenord är för kort, ska rätt exception kastas.
     */
    @Test
    void throwsConstraintViolationException_register(){
        Users newUserShort = new Users(0, "aa", "Alexander123");
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> userService.addUser(newUserShort));

        Users newUserLong = new Users(0, "aaaaaaaaaaaaaaaaaaaaaaaaaa", "Alexander123");
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> userService.addUser(newUserLong));

    }


    @Test
    void shouldRemoveUser_delete(){
        Users newUserShort = new Users(0, "Alexander123", "Alexander123");
        userRepo.save(newUserShort);

        userService.removeUser(newUserShort.getId());
        Optional<Users> foundU = userRepo.findById(newUserShort.getId());
        Assertions.assertTrue(foundU.isEmpty());

    }

}

