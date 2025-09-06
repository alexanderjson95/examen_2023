package com.example.frontend_android.repository

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Chat.MessageRequest
import com.example.frontend_android.model.Chat.MessageResponse
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.ProjectResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
     val apiInterface: API,
) {

    suspend fun getAllMessages(): Result<List<MessageResponse>> =
        withContext(Dispatchers.IO){
            Log.d("MovieLike","hey2")
            try {
                val response = apiInterface.getAllMessages()
                if (response.isSuccessful) {
                    Result.success(response.body().orEmpty())
                }
                else{
                    Result.failure(Exception("Error ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun sendMessage(req: MessageRequest): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiInterface.sendMessage(req)
                if (response.isSuccessful) Result.success(Unit)
                else Result.failure(Exception("Error ${response.code()} ${response.message()}"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }



}