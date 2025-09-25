package com.example.frontend_android.model.Projects


/**
 * DTO för projekt
 */
data class ProjectRequest (
     val projectName: String? = null,

     val description: String? = null,
     val isPublic: Boolean? = null,

     val genre: String? = null
)