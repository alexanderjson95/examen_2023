package com.example.frontend_android.ui.schedule

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingRequestPatch
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Bookings.UserBookingPatch
import com.example.frontend_android.model.Projects.UserProjectResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<BookingRequest, BookingResponse, BookingRequestPatch ,API>() {

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
        data: BookingRequestPatch
    ): Response<Unit> {
        return api.patchBooking(data.bookingId!!, data)
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
        toRemove: Long,): Response<Unit> {
        return api.removeBooking(toRemove)
    }

    override suspend fun performRemovePair(
        api: API,
        toRemove: Long,
        fromTable: Long,
    ): Response<Unit> {
        TODO("Not yet implemented")
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

    suspend fun getByBookingId(bookingId: Long): Result<List<BookingResponse>>
            = withContext(Dispatchers.IO){
        try {
            val response = apiInterface.getAllBookingsById(bookingId)
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

    suspend fun getAvailableUsers(projectId: Long, datemillis: Long): Result<List<UserProjectResponse>>
            = withContext(Dispatchers.IO){
        try {
            val response = apiInterface.getAvailableUsers(projectId, datemillis)
            val body = response.body()
            if (response.isSuccessful && body != null)
                Result.success(body)
            else
                Result.failure(Exception("HTTP ${response.code()} ${response.message()}"))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

}