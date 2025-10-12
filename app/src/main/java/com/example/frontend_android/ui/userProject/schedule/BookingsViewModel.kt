package com.example.frontend_android.ui.userProject.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.model.Bookings.BookingRequest
import com.example.frontend_android.model.Bookings.BookingRequestPatch
import com.example.frontend_android.model.Bookings.BookingResponse
import com.example.frontend_android.model.Bookings.UserBookingPatch
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectRequestPatch
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.repository.UserProjectRepository
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.repository.UserRoleRepository
import com.example.frontend_android.ui.schedule.BookingRepository
import com.example.frontend_android.ui.schedule.UserBookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingsViewModel  @Inject constructor(
    private val repo: BookingRepository,
    private val ubRepo: UserBookingRepository,

    private val upRepo: UserProjectRepository,
    private val uRepo: UserRepository,
    private val userRoleRepo: UserRoleRepository,
    private val sm: SessionManager
): ViewModel() {
    private lateinit var request: BookingRequest
    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status
    private lateinit var requestP: UserProjectRequest
    private lateinit var requestUPPatch: UserBookingPatch

    private lateinit var requestBookingPatch: BookingRequestPatch

    private val _projects = MutableLiveData<List<UserProjectResponse>>()
    val projects: LiveData<List<UserProjectResponse>> = _projects



    private val _user = MutableLiveData<UserResponse?>()
    val user: MutableLiveData<UserResponse?> = _user

    private val _userroles = MutableLiveData<List<UserRoleResponse>>()
    val userroles: MutableLiveData<List<UserRoleResponse>> = _userroles
    private val _userMember = MutableLiveData<List<UserProjectResponse>>()
    val userMember: LiveData<List<UserProjectResponse>> = _userMember
    private val _bookings = MutableStateFlow<List<BookingResponse>>(emptyList())
    val bookings: StateFlow<List<BookingResponse>> = _bookings.asStateFlow()


    fun getUserId(): Long{
        val id = sm.getId()
        if (id != null){
            return id
        }
        return 0L
    }


    init {
        getBooking(getUserId())

    }


    fun removeBooking( bookingId: Long){
        viewModelScope.launch {
            val result = repo.deleteData(bookingId)
            result.fold(
                onSuccess = { list ->
                    "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)

                }
            )
        }
    }


    fun getAccepted( projectId: Long){
        viewModelScope.launch {
            val result = upRepo.searchUserProjects("accepted",projectId)
            result.fold(
                onSuccess = { list ->
                    _members.postValue(list)
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)

                }
            )
        }
    }

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


    fun patchBooking(bookingId: Long,projectId: Long, userId:List<Long>, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, title: String, dateMillis: Long) {
        viewModelScope.launch {
            requestBookingPatch = BookingRequestPatch(
                bookingId = bookingId,
                dateMillis = dateMillis,
                startHour =startHour,
                startMinute = startMinute,
                endHour = endHour,
                endMinute = endMinute,
            )
            val result = repo.updateData(requestBookingPatch)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
        }
    }

    fun addBooking(projectId: Long, userId:List<Long>, startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, title: String, dateMillis: Long) {
        viewModelScope.launch {
            Log.d("Booking","Logging $title")
            request = BookingRequest(
                dateMillis = dateMillis,
                startHour =startHour,
                startMinute = startMinute,
                endHour = endHour,
                endMinute = endMinute,
                bookingTitle = title,
                bookingDescription = "",
                projectId = projectId,
                userIds = userId,
            )

                val result = repo.addData(request)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
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
