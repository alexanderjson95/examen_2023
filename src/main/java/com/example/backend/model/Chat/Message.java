package com.example.backend.model.Chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
    @Table(name="messages")
    @Getter
    @Setter
    public class Message {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Lob
        private String encryptedValue;
        @CreationTimestamp
        private LocalDateTime created;

}
