package com.example.frontend_android.repository

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.ui.user.RoleResponse
import com.example.frontend_android.ui.user.RoleType
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiInterface: API,
) {
    private lateinit var userRequest: UserRequest


    suspend fun getAllRoleTypes(): Result<List<RoleResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getAllRoles()
                Log.d("role","${response.body()}")
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Role error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    suspend fun returnUser(): Result<UserResponse?> = withContext(Dispatchers.IO) {
        try {
            val response = apiInterface.returnUser()

            if (response.isSuccessful) {

                Result.success(response.body())
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchUsers(query: String, value: String): Result<List<UserResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.searchUsers(query, value)
                if (response.isSuccessful) {

                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun getAll(): Result<List<UserResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.getAllUsers()
                if (response.isSuccessful) {
                    Log.d("MovieLike", "users: ${response.body()}")
                    Result.success(response.body().orEmpty())
                } else {
                    Result.failure(Exception(" getAll Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }


    suspend fun registerUser(request: UserRequest): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.registerUser(request)
                val gson = Gson()
                Log.d("API", "Outgoing JSON: ${gson.toJson(request)}")
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Register error´: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}

