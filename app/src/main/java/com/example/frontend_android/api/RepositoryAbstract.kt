package com.example.frontend_android.api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class RepositoryAbstract<req,resp,I> : RepositoryInterface<req, resp> {

    protected abstract val apiInterface: I

    protected abstract suspend fun performAdd(api: I, data: req, userId: Long? = null, targetId: Long? = null): Response<Unit>
    protected abstract suspend fun performGet(api: I, userId: Long?, targetId: Long? = null): Response<List<resp>>


    override suspend fun addData(data: req): Result<Unit>
     = withContext(Dispatchers.IO){
        try {
            val response = performAdd(apiInterface, data)
            if (response.isSuccessful)
                Result.success(Unit)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun getDataById(id: Long?): Result<List<resp>>
    = withContext(Dispatchers.IO){
        try {
            val response = performGet(apiInterface, id)
            val body = response.body()
            Log.e("BookingsViewModel", "loaded id from ab: $id")
            if (response.isSuccessful && body != null)
            Result.success(body)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }


//    override suspend fun updateData(data: T): Result<T> {}
//    override suspend fun deleteData(data: T): Result<T> {}



}