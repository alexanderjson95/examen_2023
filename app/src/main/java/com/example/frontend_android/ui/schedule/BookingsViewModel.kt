package com.example.frontend_android.ui.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.repository.UserProjectRepository
import com.example.frontend_android.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingsViewModel  @Inject constructor(
    private val repo: BookingRepository,
    private val upRepo: UserProjectRepository,
    private val uRepo: UserRepository

): ViewModel() {


    private lateinit var request: BookingRequest
    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status

    private val _projects = MutableLiveData<List<UserProjectResponse>>()
    val projects: LiveData<List<UserProjectResponse>> = _projects

    private val _user = MutableLiveData<UserResponse?>()
    val user: MutableLiveData<UserResponse?> = _user
    private val _members = MutableLiveData<List<UserProjectResponse>>()
    val members: LiveData<List<UserProjectResponse>> = _members

    private val _userMember = MutableLiveData<List<UserProjectResponse>>()
    val userMember: LiveData<List<UserProjectResponse>> = _userMember

    private val _bookings = MutableLiveData<List<BookingResponse>>()
    val bookings: LiveData<List<BookingResponse>> = _bookings



    fun getAllProjects(){
        viewModelScope.launch {
            val result = upRepo.getAllDataById()
            result.fold(
                onSuccess = { list ->
                    _projects.postValue(list)
                    _status.value = "success"
                    Log.d("getprojekt", "project function works: ID:  ${list.first().projectId}", )

                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading userprojects", e)
                    _status.value = "error"
                }
            )
        }
    }





    fun getMember(projectId: Long?){
        viewModelScope.launch {
            val result = upRepo.getAllMembersById(projectId)
            result.fold(
                onSuccess = { list ->
                    _members.postValue(list)
                    Log.d("GetMember", "Member function works: Fetched:  ${list.first().firstName}", )
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("GetMember", "Member function Error: Error loading userprojects", e)
                    _status.value = "error"
                }
            )
        }
    }

    fun getUser() {
        viewModelScope.launch {
            val result = uRepo.returnUser()
            result.fold(
                onSuccess = { user ->
                    _user.postValue(user)
                    Log.d("GetMemberUser", "Member function works: Fetched:  ${user?.id}", )
                    _status.value = "success"

                },
                onFailure = { e ->
                    Log.e("GetMemberUser", "Member function Error: Error loading userprojects", e)
                    _status.value = "error"
                }
            )
        }
    }

    fun patchBooking(bookingId: Long, projectId: Long?, userId: Long?, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, availability: Boolean, dateMillis: Long, accepted: Boolean){
        viewModelScope.launch {
            request = BookingRequest(
                projectId = projectId,
                userId = userId!!,
                dateMillis = dateMillis,
                startHour = startHour,
                startMinute = startMinute,
                endHour = endHour,
                availability = availability,
                endMinute = endMinute,
                accepted = accepted,
                )
            val result = repo.updateData(bookingId,request)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
            Log.d("AddReportViewModel: ", "Booking API Response: ${_status.value} and availability:  $availability")
        }
    }

    fun addBooking(projectId: Long?, userId: Long?, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, availability: Boolean, dateMillis: Long) {
        viewModelScope.launch {
            request = BookingRequest(
                projectId = projectId,
                userId = userId!!,
                dateMillis = dateMillis,
                startHour = startHour,
                startMinute = startMinute,
                endHour = endHour,
                availability = availability,
                endMinute = endMinute,
                accepted = false,
            )
            val result = repo.addData(request)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
            Log.d("AddReportViewModel: ", "Booking API Response: ${_status.value} and availability:  $availability")
        }
    }

    fun getBooking(targetId: Long?){
        Log.e("BookingsViewModel", "loaded bookingss: $targetId")
        viewModelScope.launch {
            val result = repo.getDataById(targetId)
            result.fold(
                onSuccess = { list ->
                    _bookings.postValue(list)
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
