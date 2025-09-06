package com.example.frontend_android.model.Users


data class UserResponse (
     val id: Long? = null,
     val username: String,
     val firstName: String? = null,
     val lastName: String? = null,
     val email: String? = null,
     val publicKey: String? = null
          )