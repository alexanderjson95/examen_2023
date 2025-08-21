package com.example.backend.service;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.model.Users.RegisterRequest;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.configs.KeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder enc;

    private final CryptoService crypto;
    private final KeyConfig keyConfig;

    @Autowired
    public UserService(CryptoService crypto, KeyConfig keyConfig) {
        this.crypto = crypto;
        this.keyConfig = keyConfig;
    }

    /*
      Vi gör null-checks i
       findUserById() och findUserByUsername()
     */

    /**
     * Packar up optional med null check
     * @param id  user id
     * @return Users objekt
     */
    public Users findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Användaren kunde inte hittas.")); // Kollar om null, skickar  ut "NoSuchElementException" om det är, annars returnerar värdet
    };

    /**
     * @param username
     * @return Users objekt
     */
    public Users findUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Användaren kunde inte hittas.")); // Kollar om null, skickar  ut "NoSuchElementException" om det är, annars returnerar värdet
    };


    public void removeUser(Long id) {
        Users foundUser = findUserById(id);
        userRepo.delete(foundUser);
    }
    public Users updateUser(Long id, String username, String password, String email, String publicKey) throws NoSuchAlgorithmException {
        Users existing = findUserById(id);
        existing.setUsername(username);
        existing.setPassword(enc.encode(password));
        existing.setEmail(email);
        existing.setPublicKey(publicKey);
        return userRepo.save(existing);
    }


    public void addUser(String username, String password, String email, String publicKey) throws NoSuchAlgorithmException {
        if(userRepo.findByUsername(username).isPresent()){
            throw new DataTakenException("Användarnamnet är taget");
        };
        String encodedPassword = enc.encode(password);
        SecretKey aesKey = keyConfig.secretKey();
        String encodedKey = crypto.encodeBase64_secretKey(aesKey);

        Users nUser = new Users();
        nUser.setUsername(username);
        nUser.setPassword(encodedPassword);
        nUser.setEmail(email);
        nUser.setPublicKey(publicKey);
        nUser.setSecretKey(encodedKey);
        userRepo.save(nUser);
    }

//    public boolean authenticateUser(String username, String password){
//        Users u = findUserByUsername(username);
//        return enc.matches(password, u.getPassword());
//    };



}
