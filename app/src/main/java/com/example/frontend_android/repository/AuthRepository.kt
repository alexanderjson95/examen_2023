package com.example.frontend_android.repository

import com.example.frontend_android.api.API
import com.example.frontend_android.model.auth.AuthRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


import com.example.frontend_android.api.sec.SessionManager


class AuthRepository @Inject constructor(
    private val apiInterface: API,
    private val sm: SessionManager
){

    suspend fun login(username: String, password: String): Result<Unit>
            = withContext(Dispatchers.IO){
        val req = AuthRequest(username = username, password = password)

        try {
            val response = apiInterface.login(req)
            if (response.isSuccessful) {
                val body = response.body() ?: return@withContext Result.failure(Exception("Empty body"))
                sm.set(body.token, body.userId)
                Result.success(Unit)

            } else {
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
            }
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }



}