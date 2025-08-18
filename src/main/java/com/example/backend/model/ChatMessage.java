package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
    @Table(name="messages")
    @Getter
    @Setter
    public class ChatMessage {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String value;

        @CreationTimestamp
        private LocalDateTime created;


}
