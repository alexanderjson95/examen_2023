package com.example.frontend_android.api

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Since we will use shared preferences everywere its more ideal
 * to create one instance with singleton and reuse it during runtime
 */

@Singleton
class SharedPrefsUtils @Inject constructor(private val sharedPrefs: SharedPreferences){

    fun getToken(): String? {
        return sharedPrefs.getString("token", null)
    }


}