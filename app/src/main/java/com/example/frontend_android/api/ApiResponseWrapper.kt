package com.example.frontend_android.api

sealed interface ApiResponseWrapper<out T> {
    data class Success<out T>(val data: T) : ApiResponseWrapper<T>
    data class Error<out T>(val code: Int? = null, val message: String? = null, val throwable: Throwable? = null) : ApiResponseWrapper<T>
    data object Loading : ApiResponseWrapper<Nothing>
    data object Empty : ApiResponseWrapper<Nothing>
}