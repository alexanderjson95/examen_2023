package com.example.frontend_android.ui.userProject.settings

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
class MyProjectSettingsViewModel  @Inject constructor(
    private val uRepo: UserRepository,
    private val userProjectRepo: UserProjectRepository,
    private val projectRepo: ProjectRepository,
    private val sm: SessionManager
): ViewModel() {

    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status


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

}
