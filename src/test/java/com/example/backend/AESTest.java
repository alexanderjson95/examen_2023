package com.example.backend;

import com.example.backend.Exceptions.DecryptFailedException;
import com.example.backend.configs.AESEncryptDecrypt;
import com.example.backend.configs.EllipticalDiffieHellman;
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
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.List;
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
        private EllipticalDiffieHellman dh;


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

        @Test
        void testECDHSecret() throws NoSuchAlgorithmException, InvalidKeyException {
            // Skapar nyckelpar (private och public)
            KeyPair aUser = dh.ECDHKeyPair();
            KeyPair bUser = dh.ECDHKeyPair();

            // Krypterar public nycklar så de säkert kan skickas
            String aUserEncoded = EllipticalDiffieHellman.encodePublicKey(aUser.getPublic());
            String bUserEncoded = EllipticalDiffieHellman.encodePublicKey(bUser.getPublic());

            // Använder varandras nyckelpar för att skapa gemensam hemlighet
            var abKey = EllipticalDiffieHellman.decodePublicKey(bUserEncoded);
            var baKey = EllipticalDiffieHellman.decodePublicKey(aUserEncoded);

            SecretKeySpec aSecret = EllipticalDiffieHellman.createSharedSecret(aUser.getPrivate(), abKey);
            SecretKeySpec bSecret = EllipticalDiffieHellman.createSharedSecret(bUser.getPrivate(), baKey);

            Assertions.assertNotNull(aSecret, "cant be null");
            Assertions.assertNotNull(bSecret, "cant be null");

            Assertions.assertArrayEquals(aSecret.getEncoded(), bSecret.getEncoded(), "Both should match");
        }


        @Test
        void EndToEndEncryption_Chat() throws Exception {
            // Skapar nyckelpar (private och public)
            KeyPair aUser = dh.ECDHKeyPair();
            KeyPair bUser = dh.ECDHKeyPair();

            // Krypterar public nycklar så de säkert kan skickas
            String aUserEncoded = EllipticalDiffieHellman.encodePublicKey(aUser.getPublic());
            String bUserEncoded = EllipticalDiffieHellman.encodePublicKey(bUser.getPublic());

            // Använder varandras nyckelpar för att skapa gemensam hemlighet
            var abKey = EllipticalDiffieHellman.decodePublicKey(bUserEncoded);
            var baKey = EllipticalDiffieHellman.decodePublicKey(aUserEncoded);

            SecretKeySpec aSecret = EllipticalDiffieHellman.createSharedSecret(aUser.getPrivate(), abKey);
            SecretKeySpec bSecret = EllipticalDiffieHellman.createSharedSecret(bUser.getPrivate(), baKey);

            String a_message = "Hello B!";
            String b_response = "Stop messaging me";
            String encrypt_a = aes.encryptString(a_message, aSecret);
            String encrypt_b = aes.encryptString(b_response, bSecret);


            String decrypt_a = aes.decryptString(encrypt_a, bSecret);
            String decrypt_b = aes.decryptString(encrypt_b, aSecret);

            Assertions.assertEquals(a_message, decrypt_a);
            Assertions.assertEquals(b_response, decrypt_b);
        }


    }
