package com.example.frontend_android.model.Bookings

data class UserBookingResponse (
val userId: Long,
    val bookingId: Long,
    val status: BookingStatusType
)
