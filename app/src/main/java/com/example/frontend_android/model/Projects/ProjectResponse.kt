package com.example.frontend_android.model.Projects

import java.time.LocalDateTime


data class ProjectResponse (
     val id: Long,
    val projectName: String,
     val description: String,
     val isPublic: Boolean = false,
     val salary: Double = 0.0,
    val type: String? = null,
     val created: String? = null,
     val requestRule: String? = null
)