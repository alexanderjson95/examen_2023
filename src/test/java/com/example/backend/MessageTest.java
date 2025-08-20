package com.example.backend;

import com.example.backend.Exceptions.DecryptFailedException;
import com.example.backend.model.Chat.MessageRequest;
import com.example.backend.model.Chat.UserMessages;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.UserMessageRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.CryptoService;
import com.example.backend.service.UserMessageService;
import com.example.backend.service.UserService;
import org.hibernate.annotations.Comment;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class MessageTest {


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
        Users users = new Users();
        Users userB = new Users();

        @Autowired
        private CryptoService crypto;


        @BeforeEach
        void setupUser() {
            users.setUsername("Alexander");
            users.setPassword("Alexander123");
            users.setEmail("alexander@email.com");

            userB.setUsername("Pelle");
            userB.setPassword("pelle12345");
            userB.setEmail("pelleee@email.com");
        }

        @AfterEach
        void cleanDB(){
            userMessagesRepo.deleteAll();
            userRepo.deleteAll();
        }

        @Comment("Testar meddelande funktionalitet utan kryptering. En user ska skicka och båda ska kunna få meddelandet")
        @Test
        void shouldCreateAndReturnMessage_success() throws NoSuchAlgorithmException {

            String content = "HEJ PÅ DIG!";
            userService.addUser(users);
            userService.addUser(userB);

            Users alexander = userService.findUserById(users.getId());
            Users pelle = userService.findUserById(userB.getId());

            MessageRequest req = new MessageRequest();
            req.setSenderId(alexander.getId());
            req.setRecipientId(pelle.getId());
            req.setEncryptedValue(content);
            userMessageService.sendMessage(req);

            List<UserMessages> alexanderInbox = msgService.getAllUsersMessages(alexander.getId());
            List<UserMessages> pellesInbox = msgService.getAllUsersMessages(pelle.getId());

            Assertions.assertFalse(alexanderInbox.isEmpty());
            Assertions.assertFalse(pellesInbox.isEmpty());

            Assertions.assertEquals(content, alexanderInbox.get(0).getMessage().getEncryptedValue());
            Assertions.assertEquals(content, pellesInbox.get(0).getMessage().getEncryptedValue());

        }

        @Comment("Testar end-to-end kryptering med AES GCM. " +
                "User A ska kunna skicka till User B. " +
                "Meddelandet ska först vara krypterat och sedan dekrypterat.")

        @Test
        void usersShouldReadEachOthersMessages_Authorized() throws NoSuchAlgorithmException {
            userService.addUser(users);
            userService.addUser(userB);
            // Hämtar användare
            Users alexander = userService.findUserById(users.getId());
            Users pelle = userService.findUserById(userB.getId());

            // Skapar nyckelpar
            KeyPair aKeyPair = crypto.generateKeyPair();
            KeyPair bKeyPair = crypto.generateKeyPair();

            // Skapar gemensam nyckel
            SecretKeySpec aMutualKey = crypto.createSharedSecret(aKeyPair.getPrivate(), bKeyPair.getPublic());
            SecretKeySpec bMutualKey = crypto.createSharedSecret(bKeyPair.getPrivate(), aKeyPair.getPublic());

            String alexandersMessage = "Hej Pelle!!!!!!";
            // Meddelandes krypteras med AES-GCM
            String alexanderMessage_encrypted = crypto.encryptMessage(alexandersMessage,aMutualKey);

            // Meddelandet skickas
            MessageRequest req = new MessageRequest();
            req.setSenderId(alexander.getId());
            req.setRecipientId(pelle.getId());
            req.setEncryptedValue(alexanderMessage_encrypted);

            userMessageService.sendMessage(req);
            // Meddelandena tas emot
            List<UserMessages> pellesInbox = msgService.getAllUsersMessages(pelle.getId());

            // Pelle ska nu läsa det
            String message = pellesInbox.getLast().getMessage().getEncryptedValue();
            ;
            // Men det är krypterat
            Assertions.assertNotEquals(alexandersMessage, message);

            // Nu testar vi att dekryptera med Pelles nyckel
            String alexanderMessage_decrypted = crypto.decryptMessage(message,bMutualKey);
            Assertions.assertEquals(alexandersMessage, alexanderMessage_decrypted);
        }


    @Comment("Testar end-to-end kryptering med AES GCM. " +
            "User A ska kunna skicka till User B. " +
            "Meddelandet ska först vara krypterat och sedan dekrypterat.")
    @Test
    void usersShouldReadEachOthersMessages_notAuthorized() throws NoSuchAlgorithmException {
        userService.addUser(users);
        userService.addUser(userB);
        // Hämtar användare
        Users alexander = userService.findUserById(users.getId());
        Users pelle = userService.findUserById(userB.getId());

        // Skapar nyckelpar
        KeyPair aKeyPair = crypto.generateKeyPair();
        KeyPair bKeyPair = crypto.generateKeyPair();

        // Skapar gemensam nyckel
        SecretKeySpec aMutualKey = crypto.createSharedSecret(aKeyPair.getPrivate(), bKeyPair.getPublic());
        SecretKeySpec bMutualKey = crypto.createSharedSecret(bKeyPair.getPrivate(), bKeyPair.getPublic());

        // Demo av frontend
        String alexandersMessage = "Hej Pelle!!!!!!";
        // Meddelandes krypteras med AES-GCM
        String alexanderMessage_encrypted = crypto.encryptMessage(alexandersMessage,aMutualKey);

        // Meddelandet skickas
        MessageRequest req = new MessageRequest();
        req.setSenderId(alexander.getId());
        req.setRecipientId(pelle.getId());
        req.setEncryptedValue(alexanderMessage_encrypted);

        // Demo av backend
        userMessageService.sendMessage(req);
        // Meddelandena tas emot
        List<UserMessages> pellesInbox = msgService.getAllUsersMessages(pelle.getId());

        // Pelle ska nu läsa det
        String message = pellesInbox.getLast().getMessage().getEncryptedValue();
        ;
        // Men det är krypterat
        Assertions.assertNotEquals(alexandersMessage, message);

        // Nu testar vi att dekryptera med Pelles nyckel
        Assertions.assertThrows(DecryptFailedException.class,
                () -> crypto.decryptMessage(message,bMutualKey));
    }


//
//
//        @Test
//        void shouldCreateAMessage_success() throws NoSuchAlgorithmException {
//            Users users = new Users("Alexander", "Alexander123", "alexander@hotmail.com");
//            userService.addUser(users);
//            MessageRequest msg = new MessageRequest();
//
//            projectService.createProject(project);
//            Optional<Project> foundProject = projectRepo.findById(project.getUserId());
//            Assertions.assertTrue(foundProject.isPresent());
//            Optional<UserProject> foundUserProject = uProjectRepo.findByUserAndProject(users, foundProject.get());
//            Assertions.assertTrue(foundUserProject.isPresent());
//        }
//
//        @Test
//        void shouldUpdateProject_success() throws NoSuchAlgorithmException {
//            Users users = new Users("Alexander", "Alexander123", "alexander@hotmail.com");
//            userService.addUser(users);
//            ProjectRequest project = new ProjectRequest();
//            project.setUserId(users.getId());
//            project.setProjectName("En häftig film");
//            project.setType("Långfilm");
//            project.setDescription("mycket cool film");
//            project.setSalary(0.0);
//            projectService.createProject(project);
//            Optional<Project> oldProject = projectRepo.findById(project.getUserId());
//            ProjectRequest updatedProjectReq = new ProjectRequest();
//            updatedProjectReq.setUserId(users.getId());
//            updatedProjectReq.setProjectName("En inte häftig film");
//            updatedProjectReq.setType("Långfilm");
//            updatedProjectReq.setDescription("mycket cool film");
//            updatedProjectReq.setSalary(0.0);
//            projectService.updateProject(oldProject.get().getId(), updatedProjectReq);
//            Optional<Project> updatedProject = projectRepo.findById(project.getUserId());
//            Assertions.assertNotSame(oldProject, updatedProject);
//            Assertions.assertSame(oldProject.get().getId(), updatedProject.get().getId());
//        }
//
//        @Test
//        void throwsNoSuchElementException() throws NoSuchAlgorithmException {
//            Users users = new Users("Alexander", "Alexander123", "alexander@hotmail.com");
//            userService.addUser(users);
//            ProjectRequest project = new ProjectRequest();
//            project.setUserId(10L);
//            project.setProjectName("En häftig film");
//            project.setType("Långfilm");
//            project.setDescription("mycket cool film");
//            project.setSalary(0.0);
//            Assertions.assertThrows(NoSuchElementException.class,
//                    () -> projectService.createProject(project));
//        }
//
//
//        @Test
//        void shouldRemoveAProject_success() throws NoSuchAlgorithmException {
//            Users users = new Users("Alexander", "Alexander123", "alexander@hotmail.com");
//            userService.addUser(users);
//            ProjectRequest project = new ProjectRequest();
//            project.setUserId(users.getId());
//            project.setProjectName("En häftig film");
//            project.setType("Långfilm");
//            project.setDescription("mycket cool film");
//            project.setSalary(0.0);
//            projectService.createProject(project);
//
//            UserProjectRequest req = new UserProjectRequest();
//            req.setUserId(users.getId());
//            req.setProjectId(project.getUserId());
//
//            projectService.getUserProjects(req);
//            Optional<Project> foundProject = projectRepo.findById(project.getUserId());
//            System.out.println("TITLE: " + foundProject.get().getProjectName());
//            projectService.removeProject(foundProject.get().getId());
//            Optional<Project> deletedProject = projectRepo.findById(project.getUserId());
//            Assertions.assertTrue(deletedProject.isEmpty());
//        }
    }





