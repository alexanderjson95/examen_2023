package com.example.backend.model.Bookings;

import com.example.backend.ToExport;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BookingRequest {

    @NotNull(message = "Projekt saknas")
    private Long projectId;

    @NotNull(message = "Måste innehålla minst en användare i bokningen!")
    private List<Long> userIds;

    @NotNull(message = "Datum saknas")
    private Long dateMillis;


    @NotNull(message = "Starttid saknas")
    private Integer startHour;
    @NotNull(message = "Starttid saknas")
    private Integer startMinute;

    @NotNull(message = "Sluttid saknas")
    private Integer endHour;
    @NotNull(message = "Sluttid saknas")
    private Integer endMinute;
    private BookingStatusType status;
    private String bookingTitle;


}
