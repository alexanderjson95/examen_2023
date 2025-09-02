package com.example.frontend_android.repository

import com.example.frontend_android.api.API
import com.example.frontend_android.model.Users.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiInterface: API,
){
    private lateinit var userRequest: UserRequest

    suspend fun register(username: String,password: String, email: String): Result<Unit>
    = withContext(Dispatchers.IO) {
        userRequest = UserRequest(
            username = username,
            email = email,
            password = password,
        )
        try {
            val resp = apiInterface.registerUser(userRequest)
            if (resp.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("HTTP  ${resp.code()} ${resp.message()}"))
        } catch (t: Throwable){
            Result.failure(t)
        }
    }
}