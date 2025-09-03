package com.example.frontend_android.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel  @Inject constructor(
    private val repo: ProjectRepository,
): ViewModel() {

    private val _Projects = MutableLiveData<String?>(null)
    val projects: MutableLiveData<String?> = _Projects
    private lateinit var request: ProjectRequest
    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun getProject(name: String,description: String, genre: String) {
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

}