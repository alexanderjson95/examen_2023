package com.example.frontend_android.model.Projects


data class UserProjectResponse (
    private val projectId: Long? = null,
    private val userId: Long? = null,
    private val role: String? = null,
    private val isCreator: Boolean? = null,
    private val isAdmin: Boolean? = null,
    private val hasJoined: Boolean? = null
    )