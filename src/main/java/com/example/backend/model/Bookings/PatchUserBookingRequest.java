package com.example.backend.model.Bookings;

import lombok.Getter;
import lombok.Setter;


/**
 * För update svar mellan admin och en användare
 */
@Getter
@Setter
public class PatchUserBookingRequest {
    private Long bookingId;
    private Long userId;
    private BookingStatusType status;
}
