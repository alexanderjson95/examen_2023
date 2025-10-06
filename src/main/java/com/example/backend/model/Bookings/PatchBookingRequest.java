package com.example.backend.model.Bookings;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchBookingRequest {
    private Long bookingId;
    private String bookingTitle;
    private String bookingDescription;
    private Long dateMillis;
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;

}
