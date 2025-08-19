package com.example.backend.repository;

import com.example.backend.model.Projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    //Optional<Project> findByUserId(int id);
    List<Project> findByUserId(int id);

}
