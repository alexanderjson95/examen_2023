package com.example.backend.service;

import com.example.backend.security.configs.AESEncryptDecrypt;
import com.example.backend.security.configs.EllipticalDiffieHellman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
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
}
