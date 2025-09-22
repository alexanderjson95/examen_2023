package com.example.frontend_android.model.roles

data class UserRoleResponse (
    val userId: Long,
    val roleType: String,
    val firstName: String,
    val lastName: String
)
