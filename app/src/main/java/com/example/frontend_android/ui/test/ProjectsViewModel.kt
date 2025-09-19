package com.example.frontend_android.ui.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.ProjectResponse
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.repository.ProjectRepository
import com.example.frontend_android.repository.UserProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProjectsViewModel  @Inject constructor(
    private val repo: ProjectRepository,
    private val projectRepository: ProjectRepository
): ViewModel() {


    private lateinit var request: ProjectRequest
    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status

    private val _projects = MutableLiveData<List<ProjectResponse>>()
    val projects: LiveData<List<ProjectResponse>> = _projects

    fun getAllProjects(){
        viewModelScope.launch {
            val result = projectRepository.getAllProjects()
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

    fun searchProject(query: String){}

    fun addProject(name: String,description: String, genre: String) {
        viewModelScope.launch {
            request = ProjectRequest(
                projectName = name,
                description = description,
                genre = genre
            )
            val result = repo.addData(request)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
            Log.d("AddReportViewModel: ", "Response: ${_status.value}")
        }
    }

    fun updateProject(p: ProjectResponse){}

    fun removeProject(id: Long){}

}
