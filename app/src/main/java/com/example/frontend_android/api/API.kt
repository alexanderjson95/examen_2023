package com.example.frontend_android.api

import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Chat.MessageRequest
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.auth.AuthRequest
import com.example.frontend_android.model.auth.AuthenticationResponse
import com.example.frontend_android.model.roles.RoleResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface API {
    @POST("/users/register")
    suspend fun registerUser(@Body user: UserRequest): Response<Void>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<UserResponse>

    @GET("users/all")
    suspend fun getAllUsers(): Response<List<UserResponse>>


    @POST("authenticate")
    suspend fun login(@Body req: AuthRequest): Response<AuthenticationResponse>

    @POST("refresh")
    suspend fun refresh(@Body req: AuthRequest): Response<AuthenticationResponse>

    @GET("users/roles")
    suspend fun getAllRoles(): Response<List<RoleResponse>>

    @GET("users/returnUser")
    suspend fun returnUser(): Response<UserResponse>

    @GET("users/search")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("value") value: String
    ): Response<List<UserResponse>>

    @POST("projects")
    suspend fun createProject(@Body request: ProjectRequest): Response<Unit>


    @GET("projects/search")
    suspend fun searchProjects(
        @Query("query") query: String,
        @Query("value") value: String
    ): Response<List<ProjectResponse>>

    @GET("/recieved/{userId}")
    suspend fun getRecievedMessages(
        @Path("projectId") projectId: Long,
    ): Response<List<MessageResponse>>

    @GET("messages/convo/{recipientId}")
    suspend fun openConvo(
        @Path("recipientId") recipientId: Long,
    ): Response<List<MessageResponse>>

    @GET("projects/{projectId}/users/{userId}")
    suspend fun getUserProject(
        @Path("projectId") projectId: Long?,
        @Path("userId") userId: Long?
    ): Response<List<UserProjectResponse>>

    @GET("projects/{projectId}/users")
    suspend fun getMembers(
        @Path("projectId") projectId: Long?,
    ): Response<List<UserProjectResponse>>

    @GET("users/roles/{userId}")
    suspend fun getMemberRoles(
        @Path("userId") userId: Long?,
    ): Response<List<RoleResponse>>

    @GET("users/userroles")
    suspend fun getAllUsersAndRoles(): Response<List<UserRoleResponse>>

        @GET("users/{userId}/roles")
        suspend fun getUserRoles(
            @Path("userId") userId: Long
        ): List<String>

    @PATCH("projects/{projectId}/{userId}")
    suspend fun updateUserProject(
        @Path("projectId") projectId: Long,
        @Path("userId") userId: Long,
        @Body req: UserProjectRequest
    ): Response<Unit>

    @POST("projects/{projectId}/users/{userId}")
    suspend fun addUserProject(
        @Path("projectId") projectId: Long?,
        @Path("userId") userId: Long?,
        @Body req: UserProjectRequest
        ): Response<Unit>

    @GET("projects/{projectId}/requests")
    suspend fun  getRequestsFromProject(@Path("projectId") projectId: Long): Response<List<UserProjectResponse>>

    @GET("projects/{projectId}/invites")
    suspend fun  getInvitesFromProject(@Path("projectId") projectId: Long): Response<List<UserProjectResponse>>


    @GET("projects/requests")
    suspend fun  getRequestsForUser(): Response<List<UserProjectResponse>>

    @GET("projects/invites")
    suspend fun  getInvitesFromUser(): Response<List<UserProjectResponse>>

    @GET("projects/accepted")
    suspend fun  getAcceptedUserProjects(): Response<List<UserProjectResponse>>


    @GET("projects/not-in")
    suspend fun  getProjectsUserIsNotIn(): Response<List<ProjectResponse>>


    @GET("/projects/user/projects")
    suspend fun getAllUsersProjects(): Response<List<UserProjectResponse>>

    @GET("projects")
    suspend fun getAllProjects(): Response<List<ProjectResponse>>


    @GET("messages/sent")
    suspend fun getAllMessages(): Response<List<MessageResponse>>

    @POST("messages/send")
    suspend fun sendMessage(@Body request: MessageRequest): Response<Unit>

    @POST("bookings")
    suspend fun createBooking(@Body req: BookingRequest): Response<Unit>

    @GET("/bookings/{userId}/users")
    suspend fun getUserBookings(@Path("userId") userId: Long?): Response<List<BookingResponse>>
    @GET("/bookings/projects/{projectId}")
    suspend fun getBookingsByProject(@Path("projectId") projectId: Long?): Response<List<BookingResponse>>

    @PATCH("/bookings/{bookingId}/update")
    suspend fun patchBooking(@Path("bookingId") bookingId: Long?, @Body req: BookingRequest): Response<Unit>


    @DELETE("/projects/{projectId}/users/{userId}")
    suspend fun removeUserFromProject(@Path("projectId") projectId: Long, @Path("userId") userId: Long ): Response<Unit>


}