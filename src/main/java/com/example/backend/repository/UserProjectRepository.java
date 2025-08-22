package com.example.backend.repository;

import com.example.backend.model.Projects.UserProject;
import org.springframework.data.jpa.repository.EntityGraph;
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

    @EntityGraph(attributePaths = {"user", "project"})
    Optional<UserProject> findByUserIdAndProjectId(Long userId, Long projectId);
    void deleteByProjectId(Long projectId);

}
