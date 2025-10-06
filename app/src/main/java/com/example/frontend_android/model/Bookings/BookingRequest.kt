package com.example.frontend_android.model.Bookings

import java.time.LocalDateTime


data class BookingRequest(
    val projectId: Long,
    val dateMillis: Long?,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val bookingTitle: String? = "Default",
    val bookingDescription: String,
    val userIds: List<Long> = emptyList()
)

