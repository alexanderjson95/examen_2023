package com.example.backend.repository;

import com.example.backend.model.Projects.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {


    List<UserProject> findByUserId(long id);
    List<UserProject> findByProjectId(long id);

}
