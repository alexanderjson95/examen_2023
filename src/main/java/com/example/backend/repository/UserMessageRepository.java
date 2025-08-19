package com.example.backend.repository;

import com.example.backend.model.Chat.UserMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessages, Long> {
    List<UserMessages> findByRecipientId(Long recipientId);
    List<UserMessages> findBySenderId(Long senderId);
    List<UserMessages> findByRecipientAndReadFalse(Long recipientId);
}
