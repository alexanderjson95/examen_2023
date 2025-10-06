package com.example.frontend_android.model.Bookings

data class UserBookingPatch(
    val userBookingId: Long,
    val statusType: BookingStatusType
)