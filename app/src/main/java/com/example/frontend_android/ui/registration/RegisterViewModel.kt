package com.example.frontend_android.ui.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.model.roles.RoleRequest
import com.example.frontend_android.model.roles.RoleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: UserRepository,
): ViewModel() {

    private val _roles = MutableLiveData<List<RoleResponse>>()
    val roles: LiveData<List<RoleResponse>> get() = _roles

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> get() = _status

    fun getRoles() {
        Log.e("RegisterViewModel", "sending")
        viewModelScope.launch {
            val result = repo.getAllRoleTypes()
            result.fold(
                onSuccess = { list ->
                    _roles.postValue(list)
                },
                onFailure = { e ->
                    Log.e("RegisterViewModel", "Error roles:", e)
                }
            )
        }
    }

    fun register(username: String, firstName: String, lastName: String, password: String, email: String, publicKey: String?, roles: List<RoleRequest>) {
        viewModelScope.launch {
            val request = UserRequest(
                username = username,
                firstName = firstName,
                lastName = lastName,
                password = password,
                email = email,
                publicKey = publicKey,
                roles = roles
            )

            val result = repo.registerUser(request)
            _status.value = result.fold(
                onSuccess = { true },
                onFailure = { false}
            )
        }
    }

}