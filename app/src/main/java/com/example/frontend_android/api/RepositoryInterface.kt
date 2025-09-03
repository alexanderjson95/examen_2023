package com.example.frontend_android.api

/**
 * Mall för alla CRUD repos
 */
sealed interface  RepositoryInterface <T> {
    suspend fun addData(data: T): Result<Unit>
    suspend fun getData(data: T): Result<T>
    suspend fun updateData(data: T): Result<T>
    suspend fun deleteData(data: T): Result<T>


}