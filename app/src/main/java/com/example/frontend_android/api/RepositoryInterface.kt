package com.example.frontend_android.api

import retrofit2.Response

/**
 * Mall för alla CRUD repos
 */
sealed interface  RepositoryInterface <req,resp> {
    suspend fun addData(data: req): Result<Unit>

    suspend fun getDataById(id: Long): Result<List<resp>>
    suspend fun getDataByPair(first: Long, second: Long): Result<List<resp>>

    suspend fun updateData(data: req): Result<Unit>
    suspend fun getData():Result<List<resp>>

    suspend fun deleteData(toRemove: Long, fromTableId: Long): Result<Unit>



}