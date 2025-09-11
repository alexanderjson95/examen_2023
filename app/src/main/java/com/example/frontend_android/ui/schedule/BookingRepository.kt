package com.example.frontend_android.ui.schedule

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingResponse

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<BookingRequest, BookingResponse, API>() {

    override suspend fun performAdd(
        api: API,
        data: BookingRequest,
        userId: Long?,
        targetId: Long?
    ): Response<Unit> {
        return apiInterface.createBooking(data)
    }

    override suspend fun performGet(
        api: API,
        userId: Long?,
        targetId: Long?
    ):  Response<List<BookingResponse>> {

        val response =  apiInterface.getUserBookings(userId)
        val rawBody = response.errorBody()?.string() ?: response.body()?.toString()
        Log.d("GetMemberRaw", "Raw response: $rawBody and $userId")
        return response
    }




    override suspend fun updateData(data: BookingRequest): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(id: BookingRequest): Result<Unit> {
        TODO("Not yet implemented")
    }


}


/**
 *     suspend fun getUserProjects(): Result<Unit>{
 *
 *         return TODO("Provide the return value")
 *     }
 *
 *     suspend fun add(name: String, description: String, genre: String): Result<String>
 *             = withContext(Dispatchers.IO) {
 *                 request = ProjectRequest(
 *             projectName = name,
 *             description = description,
 *             genre = genre
 *         )
 *         try {
 *             val resp = apiInterface.createProject(request)
 *             if (resp.isSuccessful) Result.success("HTTP ${resp.body()}")
 *             else Result.failure(Exception("HTTP  ${resp.code()} ${resp.message()}"))
 *         } catch (t: Throwable){
 *             Result.failure(t)
 *         }
 *     }
 */