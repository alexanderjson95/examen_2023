package com.example.frontend_android.model.Bookings

data class BookingRequestPatch(
    val bookingId: Long?,
    val startHour: Int?,
    val startMinute: Int?,
    val endHour: Int?,
    val endMinute: Int?,
    val availability: Boolean?,
    val dateMillis: Long
)