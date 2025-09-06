package com.example.frontend_android.model.Projects

import java.time.LocalDateTime


data class ProjectResponse (
    private val id: Long? = null,
    val projectName: String? = null,
    private val description: String? = null,
    private val isPublic: Boolean = false,
    private val salary: Double = 0.0,
    val type: String? = null,
    private val created: String? = null
)