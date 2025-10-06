package com.example.frontend_android.model.Projects

import com.example.frontend_android.model.roles.RoleResponse
import com.example.frontend_android.model.roles.RoleType
import com.example.frontend_android.model.roles.UserRoleResponse


data class UserProjectResponse (
    val projectId: Long,
    val userId: Long,
    val projectName: String? = null,
    val projectDescription: String? = null,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val role: List<String> = emptyList(),
    val isCreator: Boolean? = null,
    val isAdmin: Boolean,
    val hasJoined: Boolean? = null,
    val isBlocked: Boolean? = null,
    val requestedDate: String,
    val requestType: String

)
