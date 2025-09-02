package com.example.frontend_android.api

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val sp : SharedPreferences) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val orgReq = chain.request()
        val token = sp.getString("token", null)
        if (token.isNullOrEmpty()) {
            return chain.proceed(orgReq)
        }

        val newReq = orgReq.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newReq)
        }
}