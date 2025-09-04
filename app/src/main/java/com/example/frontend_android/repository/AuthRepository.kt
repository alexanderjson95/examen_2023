package com.example.frontend_android.repository

import android.R
import android.content.SharedPreferences
import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.auth.AuthRequest
import com.example.frontend_android.model.auth.AuthenticationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log
import androidx.core.content.edit

class AuthRepository @Inject constructor(
    private val apiInterface: API,    private val prefs: SharedPreferences
){

    suspend fun login(username: String, password: String): Result<Unit>
            = withContext(Dispatchers.IO){
        val req = AuthRequest(username = username, password = password)

        try {
            val response = apiInterface.login(req)
            if (response.isSuccessful) {
                val body = response.body() ?: return@withContext Result.failure(Exception("Empty body"))
                prefs.edit { putString("token", body.token) }
                Log.d("LOGIN", "Saved token: ${body.token}")
                Result.success(Unit)

            } else {
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }}