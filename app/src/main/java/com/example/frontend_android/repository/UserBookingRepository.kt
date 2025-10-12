package com.example.frontend_android.ui.schedule

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Bookings.UserBookingPatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserBookingRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<BookingRequest, BookingResponse, UserBookingPatch ,API>() {

    override suspend fun performAdd(
        api: API,
        data: BookingRequest
    ): Response<Unit> {
        return api.createBooking(data)
    }


    override suspend fun performGet(
        api: API
    ): Response<List<BookingResponse>> {
        return api.getAllBookings()
    }

    override suspend fun performPatch(
        api: API,
        data: UserBookingPatch
    ): Response<Unit> {
        return api.respondBooking(data)
    }

    override suspend fun performGetById(
        api: API,
        targetId: Long
    ): Response<List<BookingResponse>> {
        return api.getUserBookings(targetId)
    }

    override suspend fun performGetByPairs(
        api: API,
        first: Long,
        second: Long
    ): Response<List<BookingResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun performRemove(
        api: API,
        toRemove: Long
    ): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun performRemovePair(
        api: API,
        toRemove: Long,
        fromTable: Long,
    ): Response<Unit> {
        return apiInterface.removeUserBooking(toRemove,fromTable)
    }


    suspend fun removeUserBooking(userId: Long, bookingId:Long): Result<Unit>
            = withContext(Dispatchers.IO){
        try {
            val response = apiInterface.removeUserBooking(bookingId,userId)
            if (response.isSuccessful)
                Result.success(Unit)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
    suspend fun getByProjectId(projectId: Long): Result<List<BookingResponse>>
            = withContext(Dispatchers.IO){
        try {
            val response = apiInterface.getBookingsByProjectId(projectId)
            val body = response.body()
            if (response.isSuccessful && body != null)
                Result.success(body)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
     suspend fun respondRequest(data: UserBookingPatch): Result<Unit>
            = withContext(Dispatchers.IO){
         Log.d("PATCH", "Reaxching respond!")

         try {
            val response = apiInterface.respondBooking(data)
            if (response.isSuccessful)
                Result.success(Unit)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

}