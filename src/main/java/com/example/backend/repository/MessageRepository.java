package com.example.backend.repository;

import com.example.backend.model.Chat.Message;
import com.example.backend.model.Chat.UserMessages;
import com.example.backend.model.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Kan senare användas för admin funktioner
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}


