package com.example.frontend_android.ui.schedule

import android.util.Log
import com.example.frontend_android.api.API
import com.example.frontend_android.api.RepositoryAbstract
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingRequestPatch
import com.example.frontend_android.model.Bookings.BookingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    override val apiInterface: API,
) : RepositoryAbstract<BookingRequest, BookingResponse, API>() {

    override suspend fun performAdd(
        api: API,
        data: BookingRequest
    ): Response<Unit> {
        return apiInterface.createBooking(data)
    }


    override suspend fun performGet(
        api: API
    ): Response<List<BookingResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun performPatch(
        api: API,
        data: BookingRequest
    ): Response<Unit> {
        return apiInterface.patchBooking(data.bookingId, data)
    }

    override suspend fun performGetById(
        api: API,
        targetId: Long
    ): Response<List<BookingResponse>> {
        return apiInterface.getUserBookings(targetId)
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
        toRemove: Long,
        fromTableId: Long
    ): Response<Unit> {
        TODO("Not yet implemented")
    }


}