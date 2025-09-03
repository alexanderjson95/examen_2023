package com.example.frontend_android.api

import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.auth.AuthRequest
import com.example.frontend_android.model.auth.AuthenticationResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton



interface API {
    @POST("users/register")
    suspend fun registerUser(@Body user: UserRequest): Response<Void>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<UserResponse>

    @POST("authenticate")
    suspend fun login(@Body req: AuthRequest): Response<AuthenticationResponse>

    @POST("refresh")
    suspend fun refresh(@Body req: AuthRequest): Response<AuthenticationResponse>

    @POST("projects")
    suspend fun createProject(@Body request: ProjectRequest): Response<Unit>

    @GET("projects/{projectId}/users/{userId}")
    suspend fun getUserProject(
        @Path("projectId") projectId: Long,
        @Path("userId") userId: Long
    ): Response<UserProjectResponse>

    @PATCH("projects/{projectId}/users/{userId}")
    suspend fun updateUserProject(
        @Path("projectId") projectId: Long,
        @Path("userId") userId: Long,
        @Body req: UserProjectRequest
    ): Response<UserProjectResponse>





}