package com.example.backend;

import com.example.backend.Exceptions.DecryptFailedException;
import com.example.backend.configs.AESEncryptDecrypt;
import com.example.backend.configs.KeyConfig;
import com.example.backend.model.Users;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.ProjectService;
import com.example.backend.service.UserService;
import org.junit.Assert;
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

import javax.crypto.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class AESTest {



        @Container
        @ServiceConnection
        static MySQLContainer<?> sql = new MySQLContainer<>(
                DockerImageName.parse("mysql:8.1")
        )
                .withDatabaseName("db")
                .withUsername("u")
                .withPassword("p");
        @Autowired
        private ProjectService pService;
        @Autowired
        private ProjectRepository pRepo;
        @Autowired
        private AESEncryptDecrypt aes;
        @Autowired
        private UserService uService;
        @Autowired
        private KeyConfig keyConfig;


    @Autowired
        private UserRepository uRepo;

        @BeforeEach
        void cleanDB() {
            uRepo.deleteAll();
            pRepo.deleteAll();
        }

        @Test
        public void shouldEncryptDecrypt() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
            SecretKey key = keyConfig.secretKey();
            String projectName = "Last ticket to Hawaii";
            String encoded = aes.encryptString(projectName, key);
            System.out.println(projectName);
            System.out.println(encoded);
            Assertions.assertNotEquals(projectName,encoded);
            String decoded = aes.decryptString(encoded,key);
            System.out.println(decoded);
        }

        @Test
        public void shouldThrowTagMismatch() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
            SecretKey key = keyConfig.secretKey();
            String projectName = "Last ticket to Hawaii";
            String encoded = aes.encryptString(projectName, key);
            SecretKey badKey = keyConfig.secretKey();
            Assertions.assertThrows(DecryptFailedException.class, () -> {
                aes.decryptString(encoded, badKey);
            });
        }
    }
