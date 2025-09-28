package com.example.frontend_android.ui.MyProject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.repository.UserProjectRepository
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.repository.UserRoleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectRequestViewModel @Inject constructor(

    private val upRepo: UserProjectRepository,
    private val urRepo: UserRoleRepository,
    private val uRepo: UserRepository): ViewModel() {
    private val _userRequests = MutableLiveData<List<UserProjectResponse>>()
    val requests: LiveData<List<UserProjectResponse>> = _userRequests

    private val _users = MutableLiveData<List<UserRoleResponse>>()
    val users: LiveData<List<UserRoleResponse>> = _users
    private val _userInvites = MutableLiveData<List<UserProjectResponse>>()
    val invites: LiveData<List<UserProjectResponse>> = _userInvites

    private val _userss = MutableLiveData<List<UserResponse>>()
    val userss: LiveData<List<UserResponse>> = _userss

    fun searchUsers(query: String, value: String) {
        viewModelScope.launch {
            val result = uRepo.searchUsers(query, value)
            result.fold(
                onSuccess = { list ->
                    _userss.postValue(list)
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                }
            )
        }
    }

    fun getNonMembers() {
        viewModelScope.launch {
            val result = urRepo.getData()
            result.fold(
                onSuccess = { list ->
                    _users.postValue(list)
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                }
            )
        }
    }


    fun acceptInvite(projectId: Long, userId: Long) {
        viewModelScope.launch {
            val req = UserProjectRequest(
                userId = userId,
                projectId = projectId,
                role = " ",
                isAdmin = false,
                joined = false,
                requestType = "ACCEPTED"
            )
            val result = upRepo.updateData(
                data = req
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

    fun remove(userId: Long, projectId: Long) {
        viewModelScope.launch {
            val result = upRepo.deleteData(userId, projectId)
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
        fun sendInviteToUser(userId: Long, projectId: Long) {
            viewModelScope.launch {
                val request = UserProjectRequest(
                    userId = userId,
                    projectId = projectId,
                    role = "",
                    isAdmin = false,
                    joined = false,
                    requestType = "INVITE"
                )
                val result = upRepo.addData(request)
                result.fold(
                    onSuccess = { list ->
                        Log.e("AllProjectsViewModel", "Error loading users: ")
                    },
                    onFailure = { e ->
                        Log.e("AllProjectsViewModel", "Error loading users: ")
                    }
                )
            }
        }

        fun getRequests(projectId: Long) {
            viewModelScope.launch {
                val result = upRepo.getProjectRequests(projectId)
                result.fold(
                    onSuccess = { list ->
                        _userRequests.postValue(list)
                    },
                    onFailure = { e ->
                        Log.e("ProjectViewModel", "Error loading projects", e)
                    }
                )
            }
        }

        fun getInvites(projectId: Long) {
            viewModelScope.launch {
                val result = upRepo.getProjectInvites(projectId)
                result.fold(
                    onSuccess = { list ->
                        _userInvites.postValue(list)
                    },
                    onFailure = { e ->
                        Log.e("ProjectViewModel", "Error loading projects", e)
                    }
                )
            }
        }


    }

