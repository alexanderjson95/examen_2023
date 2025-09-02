package com.example.frontend_android.repository

import android.R
import android.content.SharedPreferences
import com.example.frontend_android.api.API
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.auth.AuthRequest
import com.example.frontend_android.model.auth.AuthenticationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import androidx.core.content.edit

class AuthRepository @Inject constructor(
    private val apiInterface: API,    private val prefs: SharedPreferences
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

    suspend fun login(username: String, password: String): Result<Unit>
            = withContext(Dispatchers.IO){
        val req = AuthRequest(username = username, password = password)
        try {
            val response = apiInterface.login(req)
            if (response.isSuccessful) {
                val token = response.body()?.token ?: return@withContext Result.failure(Exception("Empty body"))
                prefs.edit { putString("token", token) }
                Result.success(Unit)
            } else {
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }}