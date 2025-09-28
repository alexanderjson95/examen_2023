package com.example.backend.repository;

import com.example.backend.model.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
    //Optional<Users> findByEmail(String email);

    List<Users> findAllByUsernameContainingIgnoreCase(String username);
    List<Users> findAllByFirstNameContainingIgnoreCase(String firstName);
    List<Users> findAllByLastNameContainingIgnoreCase(String lastName);
}

