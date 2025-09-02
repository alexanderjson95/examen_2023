package com.example.frontend_android.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.repository.AuthRepository
import com.example.frontend_android.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {

    private val _status = MutableStateFlow<String?>(null)
    val state: StateFlow<String?> = _status

    fun login(username: String,password: String) {
        viewModelScope.launch {
            val result = repo.login(username,password)
            _status.value = result.fold(
                onSuccess = { "success" },
                onFailure = { "Error" }
            )
        }
    }

}