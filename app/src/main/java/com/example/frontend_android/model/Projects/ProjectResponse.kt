package com.example.frontend_android.model.Projects

import java.time.LocalDateTime


data class ProjectResponse (
     val id: Long,
    val projectName: String,
     val description: String,
     val created: String? = null,
     val requestRule: String? = null
)