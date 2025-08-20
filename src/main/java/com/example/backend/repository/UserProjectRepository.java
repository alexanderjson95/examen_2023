package com.example.backend.repository;

import com.example.backend.model.Projects.Project;
import com.example.backend.model.Projects.UserProject;
import com.example.backend.model.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Hanterar CRUD för användare i projekt
 */
@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    List<UserProject> findAllProjectsByUser_Id(Long id);
    Optional<UserProject> findByUserAndProject(Users user, Project project);
    void deleteByProjectId(Long projectId);

}
