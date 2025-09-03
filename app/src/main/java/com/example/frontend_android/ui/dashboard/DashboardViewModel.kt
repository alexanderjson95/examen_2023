package com.example.frontend_android.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.api.SharedPrefsUtils
import com.example.frontend_android.repository.AuthRepository
import com.example.frontend_android.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel  @Inject constructor(
    private val repo: ProjectRepository,
): ViewModel() {

    private val _Projects = MutableLiveData<String?>(null)
    val projects: MutableLiveData<String?> = _Projects
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text




}