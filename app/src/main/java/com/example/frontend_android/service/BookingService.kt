package com.example.frontend_android.service

import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Bookings.BookingStatusType
import com.example.frontend_android.ui.schedule.BookingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class BookingService  @Inject constructor(private val repo: BookingRepository)
{

    suspend fun getAvailableAndInvites(userId: Long): Result<Pair<List<BookingResponse>,List<BookingResponse>>>{
        return repo.getDataById(userId).fold(
            onSuccess = { data ->
                val canWork = data.filter { it.status == BookingStatusType.AVAILABLE }
                val workRequest = data.filter { it.status == BookingStatusType.INVITE }
                val isBooked = data.filter {it.status == BookingStatusType.ACCEPTED }
                Result.success(canWork to workRequest)
            },
            onFailure = { e -> Result.failure(e)}
        )
    }




    suspend fun getAcceptedRejected(userId: Long): Result<Pair<List<BookingResponse>,List<BookingResponse>>>{
        return repo.getDataById(userId).fold(
            onSuccess = { data ->
                val accepted = data.filter { it.status == BookingStatusType.ACCEPTED }
                val rejected = data.filter { it.status == BookingStatusType.DECLINED  }
                Result.success(accepted to rejected)
            },
            onFailure = { e -> Result.failure(e)}
        )
    }


}

