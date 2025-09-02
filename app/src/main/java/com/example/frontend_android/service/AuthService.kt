package com.example.frontend_android.service

import android.content.SharedPreferences
import com.example.frontend_android.model.auth.AuthRequest
import com.example.frontend_android.repository.AuthRepository
import javax.inject.Inject

class AuthService @Inject constructor(private val authRepo: AuthRepository,
                                      private val sharedPrefs: SharedPreferences

) {


    suspend fun login(username: String, password: String, email: String) {

    }


}