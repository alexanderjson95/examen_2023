package com.example.frontend_android.model.Bookings

import java.time.LocalDateTime


data class BookingRequest (
    private val projectId: Long,
    private val userId: Long? = null, // bokning för alla i projekt, lättare att hålla user null då
    private val start: LocalDateTime,
    private val end: LocalDateTime
)