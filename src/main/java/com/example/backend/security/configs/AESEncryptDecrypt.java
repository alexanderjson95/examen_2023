package com.example.backend.security.configs;

import com.example.backend.Exceptions.DecryptFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Component
public class AESEncryptDecrypt {


    @Autowired
    KeyConfig keyConfig;

    private static final String algo = "AES/GCM/NoPadding";
    // 12 random bytes
    private static final int iv_length = 12;
    // 16 bytes för auth tag
    private static final int auth_length = 128;
    /**
     * Skapar en initiliaserings vektor på 12 bytes med random värden
     * för att förhindra att kryptering blir för förutsägbart
     */
    public  GCMParameterSpec generateInitVector(){
        // Skapar vektor med 12 byte "längd"
        byte[] iv = new byte[iv_length];
        // Fyller med random bytes
        new SecureRandom().nextBytes(iv);
        // skickar ut längd på auth vektorn och iv vektor med random värden
        return new GCMParameterSpec(auth_length,iv);
    }

    public  String encryptString(String input, SecretKey key)  {
        try{
            // Skapar IV vektor (12 bytes längd ) + längden på auth vektor
            GCMParameterSpec ivSpec = generateInitVector();
            // Hämtar IV bytes så IV kan förvaras med datan (behöver ej krypteras som nyckeln)
            byte[] iv = ivSpec.getIV();
            // Skapar round keys (15 i mitt fall) och skapar startblocket (IV)
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            // Konverterar String till en vektor med bytes
            byte[] inputB = input.getBytes(StandardCharsets.UTF_8);
            // Skapar och fyller auth tag vektorn och krypterar allt
            byte[] encodedString = cipher.doFinal(inputB);
            // Skapar en matris med längd av iv + input och fyller med iv data + input data
            byte[] output = ByteBuffer.allocate(iv.length + encodedString.length).put(iv).put(encodedString).array();
            return Base64.getEncoder().encodeToString(output);
        }catch (Exception e){
            throw new RuntimeException("Failed to encrypt", e);
        }
    }

    public  String decryptString(String crypted_data, SecretKey key) {
        try {
            byte[] data = Base64.getDecoder().decode(crypted_data);
            // Skapar kopia av första 12 bytes (alltså IV)
            byte[] iv = Arrays.copyOfRange(data, 0, iv_length);
            // Skapar kopia av allt efter IV
            byte[] cipherData = Arrays.copyOfRange(data,iv_length,data.length);
            Cipher cipher = Cipher.getInstance(algo);
            // Skapar nu spec med IV data från hämtad krypterad data med samma auth_tag längd
            GCMParameterSpec spec = new GCMParameterSpec(auth_length, iv);
            cipher.init(Cipher.DECRYPT_MODE, key,spec);
            return new String(cipher.doFinal(cipherData), StandardCharsets.UTF_8);
        } catch (BadPaddingException e){
            throw new DecryptFailedException("Autentisering misslyckades", e);
        }
        catch (Exception e){
            throw new RuntimeException("Dekryptering misslyckades", e);
        }
    }
}
