package com.example.frontend_android.repository

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.Users.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiInterface: API,
) {
    private lateinit var userRequest: UserRequest


    suspend fun searchUsers(query: String, value: String): Result<List<UserResponse>>
    = withContext(Dispatchers.IO){
        try {
            val response = apiInterface.searchUsers(query,value)
            if (response.isSuccessful){

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

        suspend fun register(username: String, password: String, email: String): Result<Unit> =
            withContext(Dispatchers.IO) {
                userRequest = UserRequest(
                    username = username,
                    email = email,
                    password = password,
                )
                try {
                    val resp = apiInterface.registerUser(userRequest)
                    if (resp.isSuccessful) Result.success(Unit)
                    else Result.failure(Exception("HTTP  ${resp.code()} ${resp.message()}"))
                } catch (t: Throwable) {
                    Result.failure(t)
                }
            }


    }


