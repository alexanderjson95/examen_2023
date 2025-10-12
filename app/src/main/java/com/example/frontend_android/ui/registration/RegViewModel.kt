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
class RegViewModel @Inject constructor(
    private val repo: UserRepository,
): ViewModel() {


    // HÄMTAR ROLLER
    private val _roles = MutableLiveData<List<RoleResponse>>()
    val roles: LiveData<List<RoleResponse>> get() = _roles



    // REG INPUT:
    private val _uFname = MutableLiveData<String>()
    val firstName: LiveData<String> get() = _uFname
    private val _uLname = MutableLiveData<String>()
    val lastName: LiveData<String> get() = _uLname
    private val _uUsername = MutableLiveData<String>()
    val username: LiveData<String> get() = _uUsername
    private val _uPassword = MutableLiveData<String>()
    val password: LiveData<String>get() = _uPassword
    private val _uEmail = MutableLiveData<String>()
    val email: LiveData<String>get() = _uEmail
    private val _uRole = MutableLiveData<List<String>>()
    val role: LiveData<List<String>> get() = _uRole

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

    fun setFirstName(value: String) { _uFname.value = value }
    fun setLastName(value: String) { _uLname.value = value }
    fun setEmail(value: String) { _uEmail.value = value }
    fun setUsername(value: String) { _uUsername.value = value }
    fun setPassword(value: String) { _uPassword.value = value }
    fun setRole(value: List<String>) { _uRole.value = value }


}