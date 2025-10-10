package com.example.frontend_android.model.Bookings

    enum class BookingStatusType {
        INVITE,  //Admin till user
        AVAILABLE,  // AKA request
        ACCEPTED,
        DECLINED,
        ADMIN
    }

