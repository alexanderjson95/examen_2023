package com.example.backend.model.Bookings;

import com.example.backend.ToExport;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequest {

    @NotNull(message = "Projekt saknas")
    private Long projectId;

    private Long userId;

    private Long dateMillis;


    @NotNull(message = "Starttid saknas")
    private Integer startHour;
    @NotNull(message = "Starttid saknas")
    private Integer startMinute;

    @NotNull(message = "Sluttid saknas")
    private Integer endHour;
    @NotNull(message = "Sluttid saknas")
    private Integer endMinute;
    private boolean accepted;
    private boolean availability;
}
