package com.example.backend;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.model.Users.Users;
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

    @Autowired
    private PasswordEncoder enc;

    @BeforeEach
    void cleanDB(){
        userRepo.deleteAll();
    }
    /**
     * Test: Vi testar att skapa en användare. Vi kollar sedan att det finns i databasen.
     * @see #throwsUsernameTakenExceptionWhenUsernameExists_register() för scenario där användare redan finns
     */
    @Test
        void shouldCreateAndGetUser_register() throws NoSuchAlgorithmException {
        Users users = new Users("Alexander", "Alexander123", "alexander@hotmail.com");
        userService.addUser(users);

        Optional<Users> foundU = userRepo.findByUsername(users.getUsername());
        Assertions.assertTrue(foundU.isPresent());
    }



    /**
     * Test: Användaren försöker registrera sig med taget användarnamn. Testar med lowercase här med.
      * {@link DataTakenException} ska kastas med rätt meddelande*
     */
        @Test
        void throwsUsernameTakenExceptionWhenUsernameExists_register() throws NoSuchAlgorithmException {

        userService.addUser(new Users("Alexander", "Alexander123","alexander@hotmail.com"));
        DataTakenException e = Assertions.assertThrows(DataTakenException.class,
                () -> userService.addUser(new Users("alexander", "Alexander123","alexander@hotmail.com")));
        Assertions.assertEquals("Användarnamnet är taget", e.getMessage());
    }

    /**
     * Test: Ifall användarnamn / eller lösenord är för kort, ska rätt exception kastas.
     */
    @Test
    void throwsConstraintViolationException_register(){
        Users newUserShort = new Users("aa", "Alexander123","alexander@hotmail.com");
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> userService.addUser(newUserShort));

        Users newUserLong = new Users("aaaaaaaaaaaaaaaaaaaaaaaaaa", "Alexander123","alexander@hotmail.com");
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> userService.addUser(newUserLong));

    }


    /**
     * Test: Att användaren kan tas bort
     */
    @Test
    void shouldRemoveUser_delete(){
        Users newUser = new Users("Alexander123", "Alexander123","alexander@hotmail.com");
        userRepo.save(newUser);
        userService.removeUser(newUser.getId());
        Optional<Users> foundU = userRepo.findById(newUser.getId());
        Assertions.assertTrue(foundU.isEmpty());
    }

    @Test
    void shouldUpdateUser_update(){
        Users newUser = new Users("Alexander123", "Alexander123","alexander@hotmail.com");
        String newUsername = "ALEEEXANDERRRR";
        userRepo.save(newUser);

        newUser.setUsername(newUsername);
        userRepo.save(newUser);

        Optional<Users> updatedU = userRepo.findById(newUser.getId());
        Assertions.assertEquals(newUsername, updatedU.get().getUsername());

    }

    @Test
    void shouldFailToAuthenticate_login(){
        String username = "Alexander1";
        String password = "password123";
        Users newUser = new Users("Alexander1", "password","alexander@hotmail.com");
        userRepo.save(newUser);

        Assertions.assertFalse(userService.authenticateUser(username,password));
    }

    @Test
    void shouldAuthenticate_login() throws NoSuchAlgorithmException {
        String username = "Alexander1";
        String password = "password123";
        Users newUser = new Users(username, password,"alexander@hotmail.com");
        userService.addUser(newUser);

        Assertions.assertTrue(userService.authenticateUser(username,password));


    }


}

