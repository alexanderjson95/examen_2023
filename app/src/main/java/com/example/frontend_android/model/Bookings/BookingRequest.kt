package com.example.frontend_android.model.Bookings

data class BookingRequest(
    val userId: Long,
    val projectId: Long?,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val availability: Boolean,
    val accepted: Boolean? = false,
    val dateMillis: Long
)