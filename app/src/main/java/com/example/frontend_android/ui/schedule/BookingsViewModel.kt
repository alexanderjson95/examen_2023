package com.example.frontend_android.ui.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.repository.UserProjectRepository
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.model.roles.RoleResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.repository.UserRoleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingsViewModel  @Inject constructor(
    private val repo: BookingRepository,
    private val upRepo: UserProjectRepository,
    private val uRepo: UserRepository,
    private val userRoleRepo: UserRoleRepository
): ViewModel() {
    private lateinit var request: BookingRequest
    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status
    private lateinit var requestP: UserProjectRequest

    private val _projects = MutableLiveData<List<UserProjectResponse>>()
    val projects: LiveData<List<UserProjectResponse>> = _projects



    private val _user = MutableLiveData<UserResponse?>()
    val user: MutableLiveData<UserResponse?> = _user

    private val _userroles = MutableLiveData<List<UserRoleResponse>>()
    val userroles: MutableLiveData<List<UserRoleResponse>> = _userroles
    private val _userMember = MutableLiveData<List<UserProjectResponse>>()
    val userMember: LiveData<List<UserProjectResponse>> = _userMember

    private val _bookings = MutableLiveData<List<BookingResponse>>()
    val bookings: LiveData<List<BookingResponse>> = _bookings

    fun getAllUserRoles(){
        viewModelScope.launch {
            val result = userRoleRepo.getData()
            result.fold(
                onSuccess = { list ->
                    _userroles.postValue(list)
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading userprojects", e)
                    _status.value = "error"
                }
            )
        }
    }

    fun getAllProjects(){
        viewModelScope.launch {
            val result = upRepo.getData()
            result.fold(
                onSuccess = { list ->
                    _projects.postValue(list)
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading userprojects", e)
                    _status.value = "error"
                }
            )
        }
    }


    fun clearAll(){
        _bookings.value = emptyList()
        _members.value = emptyList()
        _projects.value = emptyList()
    }



    fun sendInvite(projectId: Long, userId: Long){
        viewModelScope.launch {

            requestP = UserProjectRequest(
                userId = userId, projectId = projectId, isAdmin = false, joined = false,
                role = " ",
                requestType = "REQUEST"
            )

            val result = upRepo.addData(requestP)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
            Log.d("AddReportViewModel: ", "Response: ${_status.value}")
        }
    }


    private val _members = MutableLiveData<List<UserProjectResponse>>()
    val members: LiveData<List<UserProjectResponse>> = _members
    fun getMember(projectId: Long){
        viewModelScope.launch {
            val result = upRepo.getDataById(projectId)
            result.fold(
                onSuccess = { list ->
                    _members.postValue(list)
                    _status.value = "success"
                },
                onFailure = { e ->
                    _status.value = "error"
                }
            )
        }
    }


    fun removeUserRequest(userId: Long,projectId: Long){
        viewModelScope.launch {
            val response = upRepo.deleteData(userId,projectId)
            //TODO
        }
    }

    private val _users = MutableLiveData<List<UserResponse>>()
    val users: LiveData<List<UserResponse>> = _users
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
    fun searchUsers(query: String, value: String){
        viewModelScope.launch {
            val result = uRepo.searchUsers(query,value)
            result.fold(
                onSuccess = { list ->
                    _users.postValue(list)

                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                    _status.value = "error"
                }
            )
        }
    }

    fun getUsers(){
        viewModelScope.launch {
            val result = uRepo.getAll()
            result.fold(
                onSuccess = { list ->
                    _users.postValue(list)
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                    _status.value = "error"
                }
            )
        }
    }





        private val _roless = MutableLiveData<List<String>>()
        val roless: LiveData<List<String>> get() = _roless


        fun getRoless(userId: Long) {
            Log.d("Roless", "fetch", )
            viewModelScope.launch {
                try {
                    _roless.value = uRepo.getUserRoless(userId)
                    Log.d("Roless", "Member function: Fetched:  ${roless.value}", )


                } catch (e: Exception) {
                    e.printStackTrace()
                    _roless.value = emptyList()
                }
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
                bookingId = bookingId,
            )
            val result = repo.updateData(request)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
            Log.d("AddReportViewModel: ", "Booking API Response: ${_status.value} and availability:  $availability")
        }
    }

    fun addBooking(bookingId: Long,projectId: Long?, userId: Long?, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, availability: Boolean, dateMillis: Long) {
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
                bookingId = bookingId,
            )
            val result = repo.addData(request)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
            Log.d("AddReportViewModel: ", "Booking API Response: ${_status.value} and availability:  $availability")
        }
    }

    fun getBooking(targetId: Long){
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
