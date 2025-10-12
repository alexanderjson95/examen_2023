package com.example.frontend_android.model.Bookings

data class BookingResponse(
    val bookingId: Long,
    val projectId: Long,
    val projectName: String,
    val bookingTitle: String,
    val bookingDescription: String,
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val dateMillis: Long,
    val status: BookingStatusType,
    val created: String
)


