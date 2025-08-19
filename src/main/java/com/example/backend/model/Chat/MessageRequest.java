package com.example.backend.model.Chat;


import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO för meddelanden
 */
public class MessageRequest {
    @NotNull(message = "Avsändare saknas")
    private Long senderId;
    @NotNull(message = "Mottagare saknas")
    private Long  recipientId;
    @NotNull(message = "Innehåll saknas")
    @Size(max = 2000, message = "Meddelandet får max vara 2000 tecken")
    private String content;
}
