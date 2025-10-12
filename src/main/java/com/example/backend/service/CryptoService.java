package com.example.backend.service;

import com.example.backend.security.configs.AESEncryptDecrypt;
import com.example.backend.security.configs.EllipticalDiffieHellman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class CryptoService {

    private final AESEncryptDecrypt aes;
    private final EllipticalDiffieHellman ecdh;

    @Autowired
    public CryptoService(AESEncryptDecrypt aes, EllipticalDiffieHellman ecdh) {
        this.aes = aes;
        this.ecdh = ecdh;
    }

    public String encryptMessage(String message, SecretKey key){
        return aes.encryptString(message,key);
    }

    public String decryptMessage(String encrypted, SecretKey key){
        return aes.decryptString(encrypted,key);
    }

    public KeyPair generateKeyPair(){
        return ecdh.ECDHKeyPair();
    }

    public  String encodeBase64_secretKey(SecretKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public  byte[] decodeBase64_secretKey(String key){
        return Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8));
    }

    public  String encodeBase64(PublicKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public  byte[] decodeBase64(String key){
        return Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8));
    }

    public SecretKeySpec createSharedSecret(PrivateKey privateKey, PublicKey publicKey){
        try {
            return EllipticalDiffieHellman.createSharedSecret(privateKey,publicKey);
        } catch (Exception e){
            throw new RuntimeException("Couldn't create secret", e);
        }
    }
    public SecretKeySpec derivesSharedSecret(String privateKey, String publicKey){
        try{
            byte[] privateBytes = decodeBase64_secretKey(privateKey);
            byte[] publicBytes = decodeBase64(publicKey);
            KeyFactory kf = KeyFactory.getInstance("EC");
            PrivateKey privateKey1 = kf.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
            PublicKey publicKey1 = kf.generatePublic(new X509EncodedKeySpec(publicBytes));
            return createSharedSecret(privateKey1,publicKey1);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
