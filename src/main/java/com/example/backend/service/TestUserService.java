package com.example.backend.service;

import com.example.backend.model.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TestUserService extends AbstractCrudService<Users, Long>{
    public TestUserService(JpaRepository<Users, Long> repo) {
        super(repo);
    }
}
