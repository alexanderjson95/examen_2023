package com.example.frontend_android.ui.userProject.projectDashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager.Companion.isInitialized
import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.model.roles.RoleResponse
import com.example.frontend_android.repository.ProjectRepository
import com.example.frontend_android.repository.UserProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach


/**
 * Håller data som ska skickas med hela tiden i alla delar av ett projekt (schema, visa användare etc)
 */
@HiltViewModel
class MyProjectViewModel  @Inject constructor(
    private val uRepo: UserRepository,
    private val userProjectRepo: UserProjectRepository,
    private val projectRepo: ProjectRepository,
    private val sm: SessionManager
): ViewModel() {
    private val _projects = MutableLiveData<List<UserProjectResponse>>()
    val projects: LiveData<List<UserProjectResponse>> = _projects
    private lateinit var request: ProjectRequest
    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status

    private val _user = MutableLiveData<UserResponse?>()
    val user: MutableLiveData<UserResponse?> = _user


    private val _admin = MutableLiveData<Boolean>()
    val admin: MutableLiveData<Boolean> = _admin

    init {
        getRoless()

    }

    fun getId(): Long {
        val id = sm.getId()
        if (id != null){
            return id
        }
        return 0L
    }

    fun deleteProject(projectId:Long) {
        viewModelScope.launch {
            val result = projectRepo.deleteDataPair(projectId, getId())
            result.fold(
                onSuccess = { user ->
                    _status.value = "Success"
                },
                onFailure = { e ->
                    _status.value = "error"
                }
            )
        }
    }

    fun deleteUserProject(projectId:Long) {
        viewModelScope.launch {
            val result = userProjectRepo.deleteDataPair(projectId, getId())
            result.fold(
                onSuccess = { user ->
                    _status.value = "Success"
                },
                onFailure = { e ->
                    _status.value = "error"
                }
            )
        }
    }

    private val _userproject = MutableLiveData<UserProjectResponse>()
    val userProject: MutableLiveData<UserProjectResponse> = _userproject
    fun getLoggedInUserProject(projectId: Long) {
        viewModelScope.launch {
            val result = userProjectRepo.getUserProject(projectId, getId())
            result.fold(
                onSuccess = { user ->
                    _userproject.postValue(user)
                    _status.value = "success"
                    _admin.postValue(user.isAdmin)
                },
                onFailure = { e ->
                    _status.value = "error"
                }
            )
        }
    }

    fun List<UserProjectResponse>.isUserAdmin(): Boolean {
        return any { it.isAdmin }
    }

    fun getUser() {
        viewModelScope.launch {
            Log.d("GetMemberUser", "TRYING", )
            val result = uRepo.returnUser()
            result.fold(
                onSuccess = { user ->
                    _user.postValue(user)
                    Log.d("GetMemberUser", "Member function works: Fetched:  ${user?.id}", )
                    _status.value = "success"

                },
                onFailure = { e ->
                    Log.e("GetMemberUser", "aaaaMember function Error: Error loading userprojects", e)
                    _status.value = "error"
                }
            )
        }
    }

    private val _roless = MutableLiveData<List<String>>()
    val roless: LiveData<List<String>> get() = _roless
    fun getRoless() {
        viewModelScope.launch {
            try {
                _roless.value = uRepo.getUserRoless(getId())
            } catch (e: Exception) {
                e.printStackTrace()
                _roless.value = emptyList()
            }
        }
    }
    private val _userRoles = MutableLiveData<List<RoleResponse>>()
    val userRoles: LiveData<List<RoleResponse>> = _userRoles

    fun getUserRoles(userId: Long?) {
        viewModelScope.launch {
            Log.d("ROLES", "TRYING", )
            val result = uRepo.getUserRoles(userId)
            result.fold(
                onSuccess = { user ->
                    _userRoles.postValue(user)
                    Log.d("ROLES", "Member function: Fetched:  ${user.forEach { it.roleType }}", )
                    _status.value = "success"

                },
                onFailure = { e ->
                    Log.e("ROLES", "Member function Error: Error loading userprojects", e)
                    _status.value = "error"
                }
            )
        }
    }




}
