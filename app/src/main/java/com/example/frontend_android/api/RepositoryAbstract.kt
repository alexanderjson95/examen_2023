package com.example.frontend_android.api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class RepositoryAbstract<req,resp,I> : RepositoryInterface<req, resp> {

    protected abstract val apiInterface: I

    protected abstract suspend fun performAdd(api: I, data: req): Response<Unit>
    protected abstract suspend fun performGet(api: I): Response<List<resp>>
    protected abstract suspend fun performPatch(api: I, data: req): Response<Unit>

    protected abstract suspend fun performGetById(api: I, targetId: Long): Response<List<resp>>
    protected abstract suspend fun performGetByPairs(api: I, first: Long, second: Long): Response<List<resp>>
    protected abstract suspend fun performRemove(api: I, toRemove: Long, fromTableId: Long): Response<Unit>


    override suspend fun addData(data: req): Result<Unit>
            = withContext(Dispatchers.IO){
        Log.d("AddProject: ", "runnniing}")
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

    override suspend fun getDataByPair(first: Long, second: Long): Result<List<resp>>
            = withContext(Dispatchers.IO){
        try {
            val response = performGetByPairs(apiInterface, first, second)
            val body = response.body()
            if (response.isSuccessful && body != null)
                Result.success(body)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
    override suspend fun getDataById(id: Long): Result<List<resp>>
            = withContext(Dispatchers.IO){
        try {
            val response = performGetById(apiInterface, id)
            val body = response.body()
            if (response.isSuccessful && body != null)
                Result.success(body)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }


    override suspend fun updateData(data: req): Result<Unit>
            = withContext(Dispatchers.IO){
        try {
            val response = performPatch(apiInterface, data)
            if (response.isSuccessful)
                Result.success(Unit)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun getData(): Result<List<resp>>
            = withContext(Dispatchers.IO){
        try {
            val response = performGet(apiInterface)
            val body = response.body()
            if (response.isSuccessful && body != null)
                Result.success(body)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
    override suspend fun deleteData(toRemove: Long, fromTableId: Long): Result<Unit>
        = withContext(Dispatchers.IO){
        try {
            val response = performRemove(apiInterface, toRemove, fromTableId)
            if (response.isSuccessful)
                Result.success(Unit)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
