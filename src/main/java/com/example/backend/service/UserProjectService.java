package com.example.backend.service;

import com.example.backend.model.Project;
import com.example.backend.model.Users;
import com.example.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserProjectService {

    @Autowired
    ProjectRepository repo;

    @Autowired
    PasswordEncoder enc;


    public Project findUserById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Användaren kunde inte hittas.")); // Kollar om null, skickar  ut "NoSuchElementException" om det är, annars returnerar värdet
    };



    public void addProject(Users user, Project project) {
        repo.save(project);
        System.out.println("Project created");
    }

}
