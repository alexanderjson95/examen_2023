package com.example.backend.model.Bookings;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class BookingRequest {

    @NotNull(message = "Projekt saknas")
    private Long projectId;

    @NotNull(message = "Avsändare saknas")
    private Long senderId;

    @NotNull(message = "Mottagare saknas")
    private Long recipientId;

    @Size(max = 25)
    private String title;
    @Size(max = 50)
    private String description;

    @NotNull(message = "Starttid saknas")
    private LocalDateTime start;
    private LocalDateTime end;
}
