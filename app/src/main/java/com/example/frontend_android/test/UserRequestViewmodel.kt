package com.example.frontend_android.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.UserProjectRequest
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
class UserRequestViewmodel @Inject constructor(
    private val upRepo: UserProjectRepository,
    private val uRepo: UserRepository): ViewModel() {


    private val _userRequests = MutableLiveData<List<UserProjectResponse>>()
    val requests: LiveData<List<UserProjectResponse>> = _userRequests

    private val _userInvites = MutableLiveData<List<UserProjectResponse>>()
    val invites: LiveData<List<UserProjectResponse>> = _userInvites


    fun getRequests() {
        Log.d("Roless", "fetch", )
        viewModelScope.launch {
            val result = upRepo.getUserRequests()
            result.fold(
                onSuccess = {
                        list -> _userRequests.postValue(list)
                },
                onFailure = { e ->
                    Log.e("ProjectViewModel", "Error loading projects", e)
                }
            )
        }
    }
    fun acceptInvite(projectId: Long, userId: Long){
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

    fun remove(userId: Long,projectId: Long){
        viewModelScope.launch {
            val result = upRepo.deleteData(userId,projectId)
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

    fun getInvites() {
        viewModelScope.launch {
            val result = upRepo.getUserInvites()
            result.fold(
                onSuccess = {
                        list -> _userInvites.postValue(list)
                },
                onFailure = { e ->
                    Log.e("ProjectViewModel", "Error loading projects", e)
                }
            )
        }
    }
}

