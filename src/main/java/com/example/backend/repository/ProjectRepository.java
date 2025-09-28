package com.example.backend.repository;

import com.example.backend.model.Projects.Project;
import com.example.backend.model.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Kan senare användas för admin funktioner
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByProjectNameContainingIgnoreCase(String projectName);

    @Query("SELECT p FROM Project p WHERE p.id NOT IN (" +
            "   SELECT up.project.id FROM UserProject up WHERE up.user.id = :userId)")
    List<Project> findAllProjectsUserIsNotIn(@Param("userId") Long userId);

//    @Query("""
//            SELECT p
//            FROM Project p
//            WHERE p.id NOT IN (
//                SELECT up.project.id
//                FROM UserProject up
//                WHERE up.user.id = :userId
//                )
//            """)
//    List<Project> findAllProjectsUserIsNotIn(@Param("userId") Long userId);
}


