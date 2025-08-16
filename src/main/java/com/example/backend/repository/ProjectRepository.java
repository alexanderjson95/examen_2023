package com.example.backend.repository;

import com.example.backend.model.Project;
import com.example.backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findByUserId(int id);


}
