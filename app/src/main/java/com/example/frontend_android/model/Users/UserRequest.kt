package com.example.frontend_android.model.Users


data class UserRequest(
    val username: String,
    val email: String,
    val password: String,
    val publicKey: String? = null,
    val secretKey: String? = null
)
