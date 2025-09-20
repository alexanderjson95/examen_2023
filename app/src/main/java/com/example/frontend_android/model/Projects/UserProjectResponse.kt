package com.example.frontend_android.model.Projects

import com.example.frontend_android.model.roles.RoleResponse


data class UserProjectResponse (
    val projectId: Long? = null,
    val userId: Long? = null,
    val projectName: String? = null,
    val projectDescription: String? = null,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val roles: List<RoleResponse> = emptyList(),
    val isCreator: Boolean? = null,
    val isAdmin: Boolean? = null,
    val hasJoined: Boolean? = null,
    val isBlocked: Boolean? = null,
    val requestedDate: String? = null
)
