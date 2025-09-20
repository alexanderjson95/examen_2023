package com.example.frontend_android.model.Projects


/**
 * DTO för projekt
 */
data class UserProjectRequest (
    val userId: Long? = null,
    val projectId: Long? = null,
    private val role: String? = null,
    private val isAdmin: Boolean? = null,
    private val joined: Boolean? = null
)