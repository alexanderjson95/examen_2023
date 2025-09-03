package com.example.frontend_android.api

import com.example.frontend_android.model.Projects.ProjectRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class RepositoryAbstract<T,  I> : RepositoryInterface<T> {

    protected abstract val apiInterface: I


    protected abstract suspend fun performAdd(api: I, data: T): Response<Unit>

    override suspend fun addData(data: T): Result<Unit>
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



//    override suspend fun getData(data: T): Result<T> {}
//    override suspend fun updateData(data: T): Result<T> {}
//    override suspend fun deleteData(data: T): Result<T> {}



}