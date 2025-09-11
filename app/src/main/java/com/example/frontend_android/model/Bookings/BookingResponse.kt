package com.example.frontend_android.model.Bookings

data class BookingResponse(
    val bookingId: Long,
    val projectId: Long,
    val projectName: String,
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val accepted: Boolean,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val dateMillis: Long,
    val created: String
)
