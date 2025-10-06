package com.example.backend.model.Bookings;

public enum BookingStatusType {
    INVITE,         //Admin till user
    AVAILABLE,        // AKA request
    ACCEPTED,
    DECLINED,      // om user/admin nekar andra part, behåller vi dem som nekad i db
    REMOVED
}
