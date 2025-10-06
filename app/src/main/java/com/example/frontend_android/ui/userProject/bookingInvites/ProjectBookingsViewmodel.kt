package com.example.frontend_android.ui.userProject.bookingInvites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingRequestPatch
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Bookings.BookingStatusType
import com.example.frontend_android.model.Bookings.UserBookingPatch
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.service.BookingService
import com.example.frontend_android.ui.schedule.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectBookingsViewmodel  @Inject constructor(
    private val repo: BookingRepository,
    private val service: BookingService,
    private val sm: SessionManager
): ViewModel() {
    private lateinit var request: BookingRequest
    private lateinit var requestPatch: BookingRequestPatch
    private lateinit var responseRequest: UserBookingPatch
    private lateinit var requestUserBookingPatch: UserBookingPatch


    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status
    private lateinit var requestP: UserProjectRequest

    private val _projects = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val projects: StateFlow<List<UserProjectResponse>> = _projects

    private val _user = MutableStateFlow<UserResponse?>(null)
    val user: StateFlow<UserResponse?> = _user
    private val _bookings = MutableStateFlow<List<BookingResponse>>(emptyList())
    val bookings: StateFlow<List<BookingResponse>> = _bookings

    private val _available = MutableStateFlow<List<BookingResponse>>(emptyList())
    val available: StateFlow<List<BookingResponse>> = _available

    private val _invites = MutableStateFlow<List<BookingResponse>>(emptyList())
    val invites: StateFlow<List<BookingResponse>> = _invites
    fun getId(): Long {
        val id = sm.getId()
        if (id != null){
            return id
        }
        return 0L
    }

    init {
        getAvailableAndInvites(getId())
    }
    
    fun changeUserBooking(bookingId: Long, status: BookingStatusType){
        viewModelScope.launch {
            requestUserBookingPatch = UserBookingPatch(
                userBookingId = bookingId,
                statusType = status
            )
            repo.respondRequest(responseRequest)
        }
    }



    fun patchBooking(bookingId: Long, projectId: Long?, userId: Long?, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, availability: Boolean, dateMillis: Long, accepted: Boolean){
        viewModelScope.launch {
            requestPatch = BookingRequestPatch(
                bookingId = bookingId,
                dateMillis = dateMillis,
                startHour = startHour,
                startMinute = startMinute,
                endHour = endHour,
                endMinute = endMinute,
            )
            val result = repo.updateData(requestPatch)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
        }
    }

    fun addBooking(projectId: Long, users: List<Long>, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, dateMillis: Long, bookingTitle:String) {
        viewModelScope.launch {
            Log.d("Booking","Logging $bookingTitle")

            request = BookingRequest(
                projectId = projectId,
                dateMillis = dateMillis,
                startHour = startHour,
                startMinute = startMinute,
                endHour = endHour,
                endMinute = endMinute,
                bookingTitle = bookingTitle,
                bookingDescription = "TODO()",
                userIds = users,
            )
            val result = repo.addData(request)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
        }
    }

    fun removeUserBooking(id: Long) {
        viewModelScope.launch {
            val result = repo.deleteData(id)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
        }
    }




    /**
     *  Eftersom detta dataset alltid kommer vara rätt så litet
     *  väljer jag att filtrera efter hämtning. Det är mer sannolikt
     *  att användaren vill ha tillgång till alla sina bokningar/förfrågningar
     *  och jobbpass på en gång i UI.
     */

    private val _projectBookings = MutableStateFlow<List<BookingResponse>>(emptyList())
    val projectbookings: StateFlow<List<BookingResponse>> = _projectBookings
    fun getAvailableAndInvites(userId: Long){
        viewModelScope.launch {
            val result = repo.getByProjectId(userId)
            result.fold(
                onSuccess = { list ->
                    _projectBookings.value = list
                },
                onFailure = { e ->
                    Log.e("BookingsViewModel", "Error loading bookings", e)
                    _status.value = "error"
                }
            )
            Log.d("BookingsViewModel: ", "Booking API Response: ${_status.value}")
        }
    }

    fun getBooking(){
        viewModelScope.launch {
            val result = repo.getData()
            result.fold(
                onSuccess = { list ->
                    _bookings.value = list
                    Log.e("BookingsViewModel", "loaded bookings: ${bookings.value}")
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("BookingsViewModel", "Error loading bookings", e)
                    _status.value = "error"
                }
            )
            Log.d("BookingsViewModel: ", "Booking API Response: ${_status.value}")
        }
    }

}

