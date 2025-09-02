package com.example.frontend_android.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: UserRepository,
): ViewModel() {

    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status

    fun register(username: String,password: String, email: String) {
        viewModelScope.launch {
            val result = repo.register(username,password,email)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
        }
    }

}