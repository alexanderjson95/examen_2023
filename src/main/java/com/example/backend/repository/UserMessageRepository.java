package com.example.backend.repository;

import com.example.backend.model.Chat.UserMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessages, Long> {
    List<UserMessages> findByRecipient_Id(Long recipientId);
    List<UserMessages> findBySender_Id(Long senderId);
    List<UserMessages> findByRecipient_IdAndReadFalse(Long recipientId);
}
