package com.example.backend.repository;

import com.example.backend.model.Chat.UserMessages;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessages, Long> {

    @Query("SELECT um FROM UserMessages um JOIN FETCH um.message WHERE um.recipient.id = :recipientId")
    List<UserMessages> findByRecipient_Id(Long recipientId);

    @Query("SELECT um FROM UserMessages um JOIN FETCH um.message WHERE um.sender.id = :senderId")
    List<UserMessages> findBySender_Id(Long senderId);

    @Query("SELECT um FROM UserMessages um JOIN FETCH um.message WHERE um.sender.id = :userId OR um.recipient.id = :userId")
    List<UserMessages> findAllUsersMessages(Long userId);

//    @Query()
//    List<UserMessages> findConvos(@Param("a") Long a, @Param("b") Long b);

    @Query("""
            SELECT DISTINCT
                CASE WHEN um.sender.id = :activeuser THEN um.recipient.id ELSE um.sender.id END
            FROM UserMessages um
            WHERE um.sender.id = :activeuser or um.recipient.id = :activeuser
            """)
    List<Long> getAllConvoIds(@Param("activeuser") Long userId);

    @Query("""
            SELECT um
            FROM UserMessages um
            JOIN FETCH um.message m
            WHERE (um.sender.id = :first AND um.recipient.id = :second)
                OR (um.sender.id = :second AND um.recipient.id = :first)
            ORDER BY m.created DESC, um.id DESC
            """)
    List<UserMessages> getConvo(@Param("first") Long first, @Param("second") Long second);

}
