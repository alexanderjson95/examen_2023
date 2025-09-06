package com.example.frontend_android.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.api.SharedPrefsUtils
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.repository.AuthRepository
import com.example.frontend_android.repository.ProjectRepository
import com.example.frontend_android.repository.UserProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel  @Inject constructor(
    private val repo: ProjectRepository,
    private val userProjectRepo: UserProjectRepository
): ViewModel() {


    private val _projects = MutableLiveData<List<UserProjectResponse>>()
    val projects: LiveData<List<UserProjectResponse>> = _projects
    private lateinit var request: ProjectRequest
    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status

    private val _upstatus = MutableStateFlow<String?>(null)
    val upStatus: StateFlow<String?> = _upstatus
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun getAllUserProjects() {
        viewModelScope.launch {
            val result = userProjectRepo.getAllDataById()
            result.fold(
                onSuccess = {
                        list -> _projects.postValue(list)
                    _upstatus.value = "success"
                },
                onFailure = { e ->
                    Log.e("ProjectViewModel", "Error loading projects", e)
                    _upstatus.value = "error"
                }
            )
        }
    }




}
