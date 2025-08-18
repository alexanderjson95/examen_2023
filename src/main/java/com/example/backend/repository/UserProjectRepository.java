package com.example.backend.repository;

import com.example.backend.model.UserProject;
import com.projectplatform.backend.model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {


    List<UserProject> findByUserId(long id);
    List<UserProject> findByProjectId(long id);

}
