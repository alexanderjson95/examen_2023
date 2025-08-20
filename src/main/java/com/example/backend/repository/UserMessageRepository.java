package com.example.backend.repository;

import com.example.backend.model.Chat.UserMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessages, Long> {

    @Query("SELECT um FROM UserMessages um JOIN FETCH um.message WHERE um.recipient.id = :recipientId")
    List<UserMessages> findByRecipient_Id(Long recipientId);

    @Query("SELECT um FROM UserMessages um JOIN FETCH um.message WHERE um.sender.id = :senderId")
    List<UserMessages> findBySender_Id(Long senderId);

    @Query("SELECT um FROM UserMessages um " +
            "JOIN FETCH um.message " +
            "WHERE um.sender.id = :userId OR um.recipient.id = :userId")
    List<UserMessages> findAllUsersMessages(Long userId);

}
