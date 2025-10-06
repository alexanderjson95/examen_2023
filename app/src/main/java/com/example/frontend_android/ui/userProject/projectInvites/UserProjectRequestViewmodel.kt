package com.example.frontend_android.ui.userProject.projectInvites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectRequestPatch
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.repository.UserProjectRepository
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.repository.UserRoleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProjectRequestViewmodel @Inject constructor(
    private val upRepo: UserProjectRepository,
    private val uRepo: UserRepository,
    private val userRoleRepo: UserRoleRepository,
    private val manager: SessionManager
): ViewModel() {


    private val _userRequests = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val requests: StateFlow<List<UserProjectResponse>> = _userRequests

    private val _userInvites = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val invites: StateFlow<List<UserProjectResponse>> = _userInvites



    fun getId(): Long {
        val id = manager.getId()
        if (id != null){
            return id
        }
        return 0L
    }


    private val _userMember = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val userMember: StateFlow<List<UserProjectResponse>> = _userMember

    private val _getRequested = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val getRequested: StateFlow<List<UserProjectResponse>> = _getRequested

    fun getRequested(projectId: Long){
        viewModelScope.launch {
            val result = upRepo.searchUserProjects("request",projectId)
            result.fold(
                onSuccess = { list ->
                    _getRequested.value = list
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)

                }
            )
        }
    }



    private val _userprojects = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val userprojects: StateFlow<List<UserProjectResponse>> = _userprojects

    private val _statusUserProjects = MutableStateFlow<Boolean?>(null)
    val statusUserProjects: StateFlow<Boolean?> =  _statusUserProjects






    fun getUserProjects(projectId:Long) {
        viewModelScope.launch {
            val result = upRepo.getDataById(projectId)
            result.fold(
                onSuccess = {
                        list -> _userprojects.value = list
                },
                onFailure = { e ->
                    Log.e("ProjectViewModel", "Error loading projects", e)
                }
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


    private val _getDeclined = MutableLiveData<List<UserProjectResponse>>()
    val getDeclined: LiveData<List<UserProjectResponse>> = _getDeclined
    fun getDeclined(projectId: Long){
        viewModelScope.launch {
            val result = upRepo.searchUserProjects("declined",projectId)
            result.fold(
                onSuccess = { list ->
                    _getDeclined.value = list
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)

                }
            )
        }
    }

    private val _getInvited = MutableLiveData<List<UserProjectResponse>>()
    val getInvited: LiveData<List<UserProjectResponse>> = _getInvited
    fun getInvited( projectId: Long){
        viewModelScope.launch {
            val result = upRepo.searchUserProjects("invite",projectId)
            result.fold(
                onSuccess = { list ->
                    _getInvited.value = list
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)

                }
            )
        }
    }



    private val _members = MutableLiveData<List<UserProjectResponse>>()
    val members: LiveData<List<UserProjectResponse>> = _members
    fun getMember(projectId: Long){
        viewModelScope.launch {
            val result = upRepo.getDataById(projectId)
            result.fold(
                onSuccess = { list ->
                    _members.value = list
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)

                }
            )
        }
    }

    private val _users = MutableLiveData<List<UserResponse>>()
    val users: LiveData<List<UserResponse>> = _users
    fun searchUsers(query: String, value: String){
        viewModelScope.launch {
            val result = uRepo.searchUsers(query,value)
            result.fold(
                onSuccess = { list ->
                    _users.value = list },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                }
            )
        }
    }
    fun getUsers(){
        viewModelScope.launch {
            val result = uRepo.getAll()
            result.fold(
                onSuccess = { list ->
                    _users.value = list
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                }
            )
        }
    }


    private val _userroles = MutableLiveData<List<UserRoleResponse>>()
    val userroles: MutableLiveData<List<UserRoleResponse>> = _userroles
    fun getAllUserRoles(){
        viewModelScope.launch {
            val result = userRoleRepo.getData()
            result.fold(
                onSuccess = { list ->
                    _userroles.value = list
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading userprojects", e)
                }
            )
        }
    }
    fun sendInvite(projectId: Long, userId: Long){
        viewModelScope.launch {

            val requestP = UserProjectRequest(
                userId = userId, projectId = projectId, isAdmin = false, joined = false,
                role = " ",
                requestType = "INVITE"
            )

            val result = upRepo.addData(requestP)
            result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
            Log.d("AddReportViewModel: ", "Response:")
        }
    }

    fun getProjectRequests(projectId: Long) {
        viewModelScope.launch {
            val result = upRepo.getProjectRequests(projectId)
            result.fold(
                onSuccess = {

                        list -> _userRequests.value = list
                        Log.d("REQUEST", "Got requests", )

                },
                onFailure = { e ->
                    Log.e("ProjectViewModel", "Error loading projects", e)
                }
            )
        }
    }
    fun acceptRequest(projectId: Long, userId: Long){
        viewModelScope.launch {
            val reqPatch = UserProjectRequestPatch(
                userId = userId,
                projectId = projectId,
                role = " ",
                requestType = "ACCEPTED"
            )
            val result = upRepo.updateData(
                data = reqPatch
            )
            result.fold(
                onSuccess = {
                    Log.e("ProjectViewModel", "Error loading projects")

                },
                onFailure = { e ->
                    Log.e("ProjectViewModel", "Error loading projects", e)
                }
            )
        }
    }

    fun remove(id: Long){
        viewModelScope.launch {
            val result = upRepo.deleteData(id)
            result.fold(
                onSuccess = {
                    Log.e("ProjectViewModel", "Error loading projects")
                },
                onFailure = { e ->
                    Log.e("ProjectViewModel", "Error loading projects", e)
                }
            )
        }
    }

    fun getProjectInvites(projectId: Long) {
        viewModelScope.launch {
            val result = upRepo.getProjectInvites(projectId)
            result.fold(
                onSuccess = {
                        list -> _userInvites.value = list
                        Log.d("REQUEST", "Got Invites", )

                },
                onFailure = { e ->
                    Log.e("ProjectViewModel", "Error loading projects", e)
                }
            )
        }
    }
}

