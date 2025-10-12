package com.example.frontend_android.model.roles

data class UserRoleRequest (
    private val userId: Long,
    private val roleType: String,
    private val firstName: String,
    private val lastName: String
)
