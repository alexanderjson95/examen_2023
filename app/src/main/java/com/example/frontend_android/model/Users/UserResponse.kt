package com.example.frontend_android.model.Users

import com.example.frontend_android.model.roles.RoleResponse


data class UserResponse(
     val id: Long,
     val username: String,
     val firstName: String,
     val lastName: String,
     val email: String,
     val roles: List<RoleResponse>
)