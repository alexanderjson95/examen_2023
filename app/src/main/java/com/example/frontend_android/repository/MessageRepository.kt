package com.example.frontend_android.repository

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Chat.MessageRequest
import com.example.frontend_android.model.Chat.MessageResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<MessageRequest, MessageResponse, API>(){


    override suspend fun performAdd(
        api: API,
        data: MessageRequest
    ): Response<Unit> {
        return apiInterface.sendMessage(data)
    }

    override suspend fun performGet(
        api: API
    ): Response<List<MessageResponse>> {
        return apiInterface.getAllMessages()
    }

    override suspend fun performPatch(
        api: API,
        data: MessageRequest,
        bookingId: Long
    ): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun performGetById(
        api: API,
        targetId: Long
    ): Response<List<MessageResponse>> {
        return apiInterface.openConvo(targetId)
    }

    override suspend fun performGetByPairs(
        api: API,
        first: Long,
        second: Long
    ): Response<List<MessageResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(id: MessageRequest): Result<Unit> {
        TODO("Not yet implemented")
    }
//
//    suspend fun getAllMessages(): Result<List<MessageResponse>> =
//        withContext(Dispatchers.IO){
//            Log.d("MovieLike","hey2")
//            try {
//                val response = apiInterface.getAllMessages()
//                if (response.isSuccessful) {
//                    Result.success(response.body().orEmpty())
//                }
//                else{
//                    Result.failure(Exception("Error ${response.code()} ${response.message()}"))
//                }
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//
//    suspend fun sendMessage(req: MessageRequest): Result<Unit> =
//        withContext(Dispatchers.IO) {
//            try {
//                val response = apiInterface.sendMessage(req)
//                if (response.isSuccessful) Result.success(Unit)
//                else Result.failure(Exception("Error ${response.code()} ${response.message()}"))
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//

}