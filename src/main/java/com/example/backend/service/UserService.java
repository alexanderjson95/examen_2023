package com.example.backend.service;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.model.Users;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;



    public void addUser(Users nUser) {
        userRepo.findByUsername(nUser.getUsername())
                .ifPresent(u -> { throw new DataTakenException("Användarnamnet är taget"); });
        userRepo.save(nUser);
        System.out.println("User created: " + nUser.getUsername());
    }

    public void removeUser(int id) {
        Users foundUser = findUserById(id);
        userRepo.delete(foundUser);
    }


    public Users findUserById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Användaren kunde inte hittas.")); // Kollar om null, skickar  ut "NoSuchElementException" om det är, annars returnerar värdet
    };

    public void authenticateUser(Users inputUser){

        userRepo.save(inputUser);
    };



}
