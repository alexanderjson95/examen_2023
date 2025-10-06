package com.example.frontend_android.model.Bookings

    enum class BookingStatusType {
        INVITE,  //Admin till user
        AVAILABLE,  // AKA request
        ACCEPTED,
        DECLINED,  // om user/admin nekar andra part, behåller vi dem som nekad i db
        REMOVED
    }

