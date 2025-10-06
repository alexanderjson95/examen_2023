package com.example.frontend_android.api

import android.content.SharedPreferences
import android.util.Log
import com.example.frontend_android.api.sec.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val sm : SessionManager) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {

        val orgReq = chain.request()
        val path = orgReq.url.encodedPath
        val publicEndpoint = path == "/authenticate" || path == "/refresh" ||  path.startsWith("/users/register")
        if (publicEndpoint) return chain.proceed(orgReq)

        val token = sm.getToken()
        Log.d("LOGGING", "token $token")
        if (token.isNullOrBlank()) {
            return chain.proceed(orgReq)
        }

        val newReq = orgReq.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newReq)
        }
}