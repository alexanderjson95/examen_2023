package com.example.backend;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.model.Projects.DataFramework;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.TestUserService;
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
    private DataFramework framework;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder enc;
    @Autowired
    TestUserService seeerv;

    String username = "Alexander";
    String password = "Alexander123";
    String email = "alexander123@mail.com";
    // Fake nyckel
    String publicKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEQGr5sZ0R0x2Xv9QeZszv9cG3WgA3dJmCw2xK0lGrg0Y0km6h8AlxV2hlYn3V6ug5pKbmI7GLTfKqkEThj9cK9A==";

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

        userService.addUser(username,password,email,publicKey);

        Optional<Users> foundU = userRepo.findByUsername(username);
        Assertions.assertTrue(foundU.isPresent());
    }


    @Test
    void testing(){
        Users user = new Users();
        user.setUsername("hkelefs");
        user.setPassword("hkelefs");
        user.setEmail("hkelefs@ldkd.com");
        user.setPublicKey("323423234");

        seeerv.save(user);
        Optional<Users> alexander = seeerv.findById(user.getId());
        Assertions.assertFalse(alexander.isEmpty());
    }

    /**
     * Test: Användaren försöker registrera sig med taget användarnamn. Testar med lowercase här med.
      * {@link DataTakenException} ska kastas med rätt meddelande*
     */
        @Test
        void throwsUsernameTakenExceptionWhenUsernameExists_register() throws NoSuchAlgorithmException {

            userService.addUser(username,password,email,publicKey);
        DataTakenException e = Assertions.assertThrows(DataTakenException.class,
                () ->         userService.addUser(username,password,email,publicKey));
        Assertions.assertEquals("Användarnamnet är taget", e.getMessage());
    }

    /**
     * Test: Ifall användarnamn / eller lösenord är för kort, ska rätt exception kastas.
     */
    @Test
    void throwsConstraintViolationException_register(){
        Assertions.assertThrows(ConstraintViolationException.class,
                () ->         userService.addUser("aa",password,email,publicKey));

        Assertions.assertThrows(ConstraintViolationException.class,
                () ->         userService.addUser("aaaaaaaaaaaaaaaaaaaaaaaaaa",password,email,publicKey));
    }


    /**
     * Test: Att användaren kan tas bort
     */
    @Test
    void shouldRemoveUser_delete() throws NoSuchAlgorithmException {
        userService.addUser(username,password,email,publicKey);
        Users alexander = userService.findUserByUsername(username);

        userService.removeUser(alexander.getId());
        Optional<Users> foundU = userRepo.findById(alexander.getId());
        Assertions.assertTrue(foundU.isEmpty());
    }


    @Test
    void shouldUpdateUser_update() throws NoSuchAlgorithmException {
        userService.addUser(username,password,email,publicKey);
        Users alexander = userService.findUserByUsername(username);
        String newUsername = "Ballexander";
        alexander.setUsername(newUsername);
        userService.addUser(newUsername,password,email,publicKey);

        Optional<Users> updatedU = userRepo.findByUsername(newUsername);
        Assertions.assertEquals(newUsername, updatedU.get().getUsername());

    }
//
//    @Test
//    void shouldFailToAuthenticate_login() throws NoSuchAlgorithmException {
//        userService.addUser(username,password,email,publicKey);
//        String falsePassword = "lösenordförsök123";
//        userService.authenticateUser(username,falsePassword);
//        Assertions.assertFalse(userService.authenticateUser(username,falsePassword));
//    }
//
//    @Test
//    void shouldAuthenticate_login() throws NoSuchAlgorithmException {
//        userService.addUser(username,password,email,publicKey);
//        Assertions.assertTrue(userService.authenticateUser(username,password));
//    }


}

