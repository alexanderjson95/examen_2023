package com.example.frontend_android.model.Bookings

data class UserBookingPatch(
    val bookingId: Long,
    val userId: Long,
    val status: BookingStatusType
)