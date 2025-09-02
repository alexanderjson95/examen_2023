package com.example.frontend_android.model.Bookings;



import java.time.LocalDateTime;

public class BookingRequest {

    private Long projectId;

    private Long senderId;

    private Long recipientId;
    private String title;
    private String description;

    private LocalDateTime start;
    private LocalDateTime end;
}
