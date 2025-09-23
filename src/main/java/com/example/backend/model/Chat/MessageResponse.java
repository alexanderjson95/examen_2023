package com.example.backend.model.Chat;

import com.example.backend.ToExport;
import com.example.backend.model.Projects.Project;
import com.example.backend.model.Projects.ProjectResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
@ToExport

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private Long senderId;
    private Long recipientId;
    private String senderFirstname;
    private String senderLastname;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;

public static MessageResponse fromMessageResponse(UserMessages um) {
    return MessageResponse.builder()
            .id(um.getId())
            .senderId(um.getSender().getId())
            .recipientId(um.getRecipient().getId())
            .senderFirstname(um.getSender().getFirstName())
            .senderLastname(um.getRecipient().getLastName())
            .content(um.getMessage().getEncryptedValue())
            .created(um.getMessage().getCreated())
            .build();
        }
    }