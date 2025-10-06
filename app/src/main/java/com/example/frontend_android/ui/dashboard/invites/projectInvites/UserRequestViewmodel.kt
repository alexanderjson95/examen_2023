package com.example.frontend_android.ui.dashboard.invites.projectInvites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.model.Bookings.BookingStatusType
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectRequestPatch
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.repository.UserProjectRepository
import com.example.frontend_android.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRequestViewmodel @Inject constructor(
    private val upRepo: UserProjectRepository,
    private val uRepo: UserRepository,
): ViewModel() {



    private val _userprojects = MutableStateFlow<List<UserProjectResponse>>(emptyList())
    val userprojects: StateFlow<List<UserProjectResponse>> = _userprojects

    private val _statusUserProjects = MutableStateFlow<Boolean?>(null)
    val statusUserProjects: StateFlow<Boolean?> =  _statusUserProjects



    init {
        getUserProjects()
    }


    fun getUserProjects() {
        viewModelScope.launch {
            val result = upRepo.getData()
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

    fun acceptInvite(projectId: Long, userId: Long){
        viewModelScope.launch {
            val req = UserProjectRequestPatch(
                userId = userId,
                projectId = projectId,
                role = " ",
                requestType = BookingStatusType.ACCEPTED.toString()
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


//    fun declineInvite(projectId: Long, userId: Long){
//        viewModelScope.launch {
//            Log.e("ProjectViewModel", "running: project: $projectId  and user:   $userId")
//            val req = UserProjectRequest(
//                userId = userId,
//                projectId = projectId,
//                role = " ",
//                isAdmin = false,
//                joined = false,
//                requestType = "DECLINED"
//            )
//            val result = upRepo.updateData(data = req)
//            result.fold(
//                onSuccess = {
//                    Log.e("ProjectViewModel", "success")
//
//                },
//                onFailure = { e ->
//                    Log.e("ProjectViewModel", "Error loading projects", e)
//                }
//            )
//        }
//    }


}

