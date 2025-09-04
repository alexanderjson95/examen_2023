package com.example.frontend_android.model.Projects

import com.example.frontend_android.model.Users.UserResponse
import java.time.LocalDateTime




data class UserProjectResponse (
    private val projectId: Long? = null,
    private val userId: Long? = null,
    val projectName: String? = null,
    private val projectDescription: String? = null,
    private val username: String? = null,
    val role: String? = null,
    private val isCreator: Boolean? = null,
    private val isAdmin: Boolean? = null,
    private val hasJoined: Boolean? = null,
    private val isBlocked: Boolean? = null,
    val requestedDate: String?  = null
    )

