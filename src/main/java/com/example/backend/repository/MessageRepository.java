package com.example.backend.repository;

import com.example.backend.model.Chat.UserMessages;
import org.apache.logging.log4j.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Kan senare användas för admin funktioner
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}


