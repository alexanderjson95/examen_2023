package com.example.backend.model.Chat;


import com.example.backend.model.Users.Users;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO för meddelanden
 */
@Getter
@Setter
public class MessageRequest {
    private Long senderId;
    private Long recipientId;
    private String encryptedValue;
}
