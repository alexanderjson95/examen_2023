package com.example.frontend_android.ui.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.repository.UserProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class BookingsViewModel  @Inject constructor(
    private val repo: BookingRepository,
    private val upRepo: UserProjectRepository
): ViewModel() {


    private lateinit var request: BookingRequest
    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status

    private val _projects = MutableLiveData<List<UserProjectResponse>>()
    val projects: LiveData<List<UserProjectResponse>> = _projects

    private val _members = MutableLiveData<List<UserProjectResponse>>()
    val members: LiveData<List<UserProjectResponse>> = _members
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


    fun addBooking(projectId: Long, userId: Long?, description: String?,start: LocalDateTime, end: LocalDateTime ) {
        viewModelScope.launch {
            request = BookingRequest(
                projectId = projectId,
                userId = userId,
                start = start,
                end = end
            )
            val result = repo.addData(request)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
            Log.d("AddReportViewModel: ", "Booking API Response: ${_status.value}")
        }
    }

}
