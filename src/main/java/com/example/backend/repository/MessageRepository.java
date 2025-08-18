package com.example.backend.repository;

import com.projectplatform.backend.model.Bookings;
import org.apache.logging.log4j.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Optional<Message> findByUserId(long id);
    Optional<Message> findByProjectId(long id);


}
