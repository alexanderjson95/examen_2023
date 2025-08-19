package com.example.backend.service;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder enc;

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
    public void updateUser(Users nUser) throws NoSuchAlgorithmException {
        addUser(nUser);

    }


    public void addUser(Users nUser) throws NoSuchAlgorithmException {

        if(userRepo.findByUsername(nUser.getUsername()).isPresent()){
            throw new DataTakenException("Användarnamnet är taget");
        };

        String hPassword = enc.encode(nUser.getPassword());
        nUser.setPassword(hPassword);
        userRepo.save(nUser);
    }
    public boolean authenticateUser(String username, String password){
        Users u = findUserByUsername(username);
        return enc.matches(password, u.getPassword());
    };



}
