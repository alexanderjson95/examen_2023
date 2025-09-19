package com.example.frontend_android.model.Users

import com.example.frontend_android.ui.user.RoleRequest
import com.example.frontend_android.ui.user.RoleType


data class UserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val publicKey: String? = null,
    val roles: List<RoleRequest>
)