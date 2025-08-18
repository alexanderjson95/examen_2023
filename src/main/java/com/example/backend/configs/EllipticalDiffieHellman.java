package com.example.backend.configs;

import org.springframework.stereotype.Component;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class EllipticalDiffieHellman {


    /* Använder för end-to-end kryptering. Istället för en standard secret key skapar vi en
    * delad nyckel som kan delas mellan flera parter. Jag använder ECDH för detta.
    *
    * 1.
    * Vi skapar en elliptisk kurva över ett ändligt fält, dessa punkter är en grupp i fältet.
    * Snabbare att gå fram, långsammare att gå bak: https://sebastiaagramunt.medium.com/discrete-logarithm-problem-and-diffie-hellman-key-exchange-821a45202d26
    *
    * 2.
    * Nu skapar vi en private key (konstant) och public key till användaren. Private key är alltid helt hemlig,
    * public key kan vi skicka mellan parterna.
    *
    * 3.
    * Alexander använder sin private key och  Pelles public key * G
    * och
    * Pelle använder sin private key och Alexander public key * G
    * båda får samma svar. Alltså en delad nyckel.
    *
    *  */


    /**
     *  Skapar nyckel par, en private(k) och public
     *  Private nyckeln delas ej, men används för att generera public key,
     *  vilket i enkla ord är en punkt på "kurvan" av punkter i det ändliga fältet.
     */
    public KeyPair ECDHKeyPair()  {
        try{
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec curve = new ECGenParameterSpec("secp256r1");
            kpg.initialize(curve, new SecureRandom());
            return kpg.generateKeyPair();
        }
        catch(Exception e){throw new RuntimeException("Error generating keypair", e);
        }
    }

    /* User A får User B's public key */

    /**
     * Base64 encoding för att skicka.
     *
     */
    public static String encodePublicKey(PublicKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Base64 decoding
     */
    public static PublicKey decodePublicKey(String key){
        try{
            byte[] keyBytes =  Base64.getDecoder().decode(key);
            KeyFactory kf = KeyFactory.getInstance("EC");
            return kf.generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch (Exception e) {
            throw new RuntimeException("Error decoding key", e);
        }

    }

    /**
     * Nu tar user A och skapar en gemensam hemlighet med sin privata nyckel och user B public nyckel
     * och får ut en byte array (gemensamma nyckeln). Gör vi det tvärtom för user B ut en symmetrisk array
     * Sedan hashar vi nyckeln  till 256-bit. nu kan vi använda nyckeln tillsammans med AES kryptering/dekryptering istället
     * för standard nyckel.
     */
    public static SecretKeySpec createSharedSecret(PrivateKey privateKey, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException {
        try{
            KeyAgreement agreement = KeyAgreement.getInstance("ECDH");
            agreement.init(privateKey);
            agreement.doPhase(publicKey, true);
            byte[] secret =  agreement.generateSecret();
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] dk = sha.digest(secret);
            return new SecretKeySpec(dk, 0, 32,"AES");
        }catch (Exception e){
            throw new RuntimeException("Couldnt create shared key", e);
        }

    }

}
