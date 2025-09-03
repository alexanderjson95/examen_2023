package com.example.frontend_android.model.Projects


/**
 * DTO för projekt
 */
data class ProjectRequest (
    private val projectName: String? = null,

    private val description: String? = null,
    private val isPublic: Boolean? = null,

    private val genre: String? = null
)