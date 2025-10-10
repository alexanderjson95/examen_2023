package com.example.frontend_android.ui.userProject.bookingInvites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingRequestPatch
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Bookings.BookingStatusType
import com.example.frontend_android.model.Bookings.UserBookingPatch
import com.example.frontend_android.model.Bookings.UserBookingResponse
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.repository.UserProjectRepository
import com.example.frontend_android.service.BookingService
import com.example.frontend_android.ui.schedule.BookingRepository
import com.example.frontend_android.ui.schedule.UserBookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectBookingsViewmodel  @Inject constructor(
    private val repo: BookingRepository,
    private val ubRepo: UserBookingRepository,
    private val service: BookingService,
    private val upRepo: UserProjectRepository,

    private val sm: SessionManager
): ViewModel() {
    private lateinit var request: BookingRequest
    private lateinit var requestPatch: BookingRequestPatch
    private lateinit var responseRequest: UserBookingPatch
    private lateinit var requestUserBookingPatch: UserBookingPatch


    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status

    private val _projects = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val projects: StateFlow<List<UserProjectResponse>> = _projects

    private val _user = MutableStateFlow<UserResponse?>(null)
    val user: StateFlow<UserResponse?> = _user
    private val _bookings = MutableStateFlow<List<BookingResponse>>(emptyList())
    val bookings: StateFlow<List<BookingResponse>> = _bookings


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
        getBooking(getId())
    }
    
    fun changeUserBooking(bookingId: Long, userId: Long, status: BookingStatusType){
        viewModelScope.launch {
            requestUserBookingPatch = UserBookingPatch(
                bookingId = bookingId,
                userId = userId,
                status = status
            )
            repo.respondRequest(responseRequest)
        }
    }

    private val _userprojects = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val userprojects: StateFlow<List<UserProjectResponse>> = _userprojects

    val accepted: Flow<List<UserProjectResponse>> = _userprojects.map { it.filter { up -> up.requestType == "ACCEPTED" }}


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


    private val _getAccepted = MutableLiveData<List<UserProjectResponse>>()
    val getAccepted: LiveData<List<UserProjectResponse>> = _getAccepted
    fun getAccepted( projectId: Long){
        viewModelScope.launch {
            val result = upRepo.searchUserProjects("accepted",projectId)
            result.fold(
                onSuccess = { list ->
                    _getAccepted.value = list
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)

                }
            )
        }
    }
    private val _available = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val available: StateFlow<List<UserProjectResponse>> = _available


    fun getAvailableMembers(projectId: Long, date:Long){
        viewModelScope.launch {
            val result = repo.getAvailableUsers(projectId,date);
            _status.value = result.fold(
                onSuccess = { list ->
                    _available.value = list
                    Log.e("BookingsViewModel", "loaded bookings: ${bookings.value}")
                    "success" },
                onFailure = { "Error" }
            )
        }
    }

    private val _statusAdd = MutableStateFlow<Boolean?>(false)
    val stateAdd: StateFlow<Boolean?> = _statusAdd
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
            _statusAdd.value = result.fold(
                onSuccess = {
                    "success"
                    true},
                onFailure = {
                    "Error"
                    false}
            )
        }
    }

    fun removeUserBooking(bookingId: Long, userId:Long) {
        viewModelScope.launch {
            val result = ubRepo.deleteDataPair(bookingId,userId )
             result.fold(
                onSuccess = {
                    getBooking(getId())
                    _bookings.update { current ->
                        current.filterNot { it.bookingId == bookingId }
                    }

                            },
                onFailure = {
                    "Error"
                }
            )
        }
    }



    fun getAllByBookingId(bookingId: Long) {
        viewModelScope.launch {
            val result = repo.getByBookingId(bookingId);
            _status.value = result.fold(
                onSuccess = { list ->
                    _bookings.value = list
                    Log.e("BookingsViewModel", "loaded bookings: ${bookings.value}")
                    "success" },
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

    fun getBooking(targetId: Long){
        Log.e("BookingsViewModel", "loaded bookingss: $targetId")
        viewModelScope.launch {
            val result = repo.getDataById(targetId)
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

