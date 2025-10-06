package com.example.frontend_android.api.sec

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {

    private var jwt: String? = null
    private var userId: Long? = null


    fun set(token: String?, id: Long?){
        jwt = token
        userId = id
    }

    fun getToken(): String?= jwt
    fun getId(): Long? = userId

    fun clear(){
        jwt = null
        userId = null
    }

    fun isLoggedIn(): Boolean = !jwt.isNullOrBlank()

}