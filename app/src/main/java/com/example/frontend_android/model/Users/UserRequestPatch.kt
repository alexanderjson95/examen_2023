package com.example.frontend_android.model.Users

import com.example.frontend_android.model.roles.RoleRequest


data class UserRequestPatch(
    val firstName: String,
    val lastName: String,
    val password: String,
    val roles: List<RoleRequest>
)