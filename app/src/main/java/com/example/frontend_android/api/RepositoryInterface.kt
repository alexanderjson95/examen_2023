package com.example.frontend_android.api

/**
 * Mall för alla CRUD repos
 */
sealed interface  RepositoryInterface <req,resp> {
    suspend fun addData(data: req): Result<Unit>

    suspend fun getDataById(id: Long): Result<List<resp>>
    suspend fun getDataByPair(first: Long, second: Long): Result<List<resp>>

    suspend fun updateData(id: Long, data: req): Result<Unit>
    suspend fun getData():Result<List<resp>>

    suspend fun deleteData(id: req): Result<Unit>



}