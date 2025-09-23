package com.example.backend.repository;

import com.example.backend.model.Projects.Project;
import com.example.backend.model.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Kan senare användas för admin funktioner
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByProjectNameContainingIgnoreCase(String projectName);

}


