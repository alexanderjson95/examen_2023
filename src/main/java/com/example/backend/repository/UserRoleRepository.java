package com.example.backend.repository;

import com.example.backend.model.roles.UserRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Kan senare användas för admin funktioner
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {


    @Query("SELECT ur.role.role FROM UserRole ur WHERE ur.user.id = :userId")
    List<String> findRoleNamesByUserId(@Param("userId") Long userId);

}

