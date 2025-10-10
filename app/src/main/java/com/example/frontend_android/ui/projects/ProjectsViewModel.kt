package com.example.frontend_android.ui.projects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.repository.ProjectRepository
import com.example.frontend_android.repository.UserProjectRepository
import com.example.frontend_android.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProjectsViewModel  @Inject constructor(
    private val repo: ProjectRepository,
    private val upRepo: UserProjectRepository,
    private val uRepo: UserRepository,
): ViewModel() {

    private lateinit var requestP: UserProjectRequest
    private lateinit var request: ProjectRequest
    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status

    private val _projects = MutableLiveData<List<ProjectResponse>>()
    val projects: LiveData<List<ProjectResponse>> = _projects

    private val _userProjects = MutableLiveData<List<UserProjectResponse>>()
    val userProjects: LiveData<List<UserProjectResponse>> = _userProjects
    private val _unaddedProjects = MutableLiveData<List<ProjectResponse>>()
    val unaddedProjects: LiveData<List<ProjectResponse>> = _unaddedProjects

    private val _user = MutableLiveData<UserResponse?>()
    val user: MutableLiveData<UserResponse?> = _user


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


    fun sendInvite(projectId: Long, userId: Long){
        viewModelScope.launch {

            requestP = UserProjectRequest(
                userId = userId, projectId = projectId, isAdmin = false, joined = false,
                role = " ",
                requestType = "REQUEST"
            )

            val result = upRepo.addData(requestP)
            getAllProjects()
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
            Log.d("AddReportViewModel: ", "Response: ${_status.value}")
        }
    }

    fun searchProjects(query: String, value: String) {
        viewModelScope.launch {
            val result = repo.searchProjects(query, value)
            result.fold(
                onSuccess = { list ->
                    _projects.postValue(list)
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                    _status.value = "error"
                }
            )
        }
    }
    fun getAllProjects() {
        viewModelScope.launch {
            val result = repo.getData()
            result.fold(
                onSuccess = { list ->
                    _projects.postValue(list)
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading projects", e)
                    _status.value = "error"
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
                    _members.postValue(list)
                    _status.value = "success"
                },
                onFailure = { e ->
                    _status.value = "error"
                }
            )
        }
    }

    fun sendRequestToProject(projectId: Long) {
        viewModelScope.launch {
            val request = UserProjectRequest(
                userId = 0L,
                projectId = projectId,
                role = "",
                isAdmin = false,
                joined = false,
                requestType = "ACCEPTED"
            )
            val result = upRepo.addData(request)
            result.fold(
                onSuccess = { list ->
                    _status.value = "success"
                },
                onFailure = { e ->
                    Log.e("AllProjectsViewModel", "Error loading users: ", e)
                    _status.value = "error"
                }
            )
        }



        fun getUnaddedProjects() {
            viewModelScope.launch {
                val result = repo.getAllUnaddedProjects()
                result.fold(
                    onSuccess = { list ->
                        _unaddedProjects.postValue(list)
                        _status.value = "success"
                    },
                    onFailure = { e ->
                        Log.e("AllProjectsViewModel", "Error loading users: ", e)
                        _status.value = "error"
                    }
                )
            }
        }

        fun addProject(name: String, description: String) {
            viewModelScope.launch {
                request = ProjectRequest(
                    projectName = name,
                    description = description,
                )
                val result = repo.addData(request)
                _status.value = result.fold(
                    onSuccess = {
                        "success" },
                    onFailure = { "Error" }
                )
                Log.d("AddReportViewModel: ", "Response: ${_status.value}")
            }
        }

    }
}

