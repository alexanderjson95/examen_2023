package com.example.frontend_android.model.Projects

//import com.example.frontend_android.test.requestType


/**
 * DTO för projekt
 */
data class UserProjectRequestPatch (
    val userId: Long,
    val projectId: Long,
    private val role: String? = null,
    private val requestType: String
)