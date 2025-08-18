package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

    @Entity
    @Table(name="messages")
    @Getter
    @Setter
    public class ChatMessage {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String value;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private Users user;
}
