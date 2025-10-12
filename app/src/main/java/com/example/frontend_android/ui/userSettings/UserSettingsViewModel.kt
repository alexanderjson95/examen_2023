package com.example.frontend_android.ui.userSettings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_android.ErrorMessages
import com.example.frontend_android.api.sec.SessionManager
import com.example.frontend_android.model.Projects.ProjectRequest
import com.example.frontend_android.model.Projects.UserProjectRequest
import com.example.frontend_android.model.Projects.UserProjectResponse
import com.example.frontend_android.model.Users.UserRequest
import com.example.frontend_android.model.Users.UserRequestPatch
import com.example.frontend_android.model.Users.UserResponse
import com.example.frontend_android.model.roles.RoleRequest
import com.example.frontend_android.repository.UserRepository
import com.example.frontend_android.model.roles.RoleResponse
import com.example.frontend_android.model.roles.RoleType
import com.example.frontend_android.model.roles.UserRoleResponse
import com.example.frontend_android.repository.UserRoleRepository
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
class UserSettingsViewModel  @Inject constructor(
    private val uRepo: UserRepository,
    private val urRepo: UserRoleRepository
): ViewModel() {

    @Inject
    lateinit var sessionManager: SessionManager
    private lateinit var request: UserRequestPatch




    private val _user = MutableLiveData<UserResponse?>()
    val user: MutableLiveData<UserResponse?> = _user

    fun getUser() {
        viewModelScope.launch {
            Log.d("GetMemberUser", "TRYING", )
            val result = uRepo.returnUser()
            result.fold(
                onSuccess = { user ->
                    _user.postValue(user)
                    Log.d("GetMemberUser", "Member function works: Fetched:  ${user?.id}", )

                },
                onFailure = { e ->
                    Log.e("GetMemberUser", "aaaaMember function Error: Error loading userprojects", e)
                }
            )
        }
    }


    init {
        getUser()
        getRoles()
        getLoggedInRole()
    }

    private val _roles = MutableLiveData<List<RoleResponse>>()
    val roles: LiveData<List<RoleResponse>> get() = _roles
    fun getRoles() {
        Log.e("RegisterViewModel", "sending")
        viewModelScope.launch {
            val result = uRepo.getAllRoleTypes()
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

    private val _userRoles = MutableLiveData<List<String>>()
    val userRoles: LiveData<List<String>> = _userRoles
    fun getLoggedInRole() {
        viewModelScope.launch {
            val result = urRepo.getLoggedInRole()
            result.fold(
                onSuccess = { user ->
                    _userRoles.postValue(user)
                            },
                onFailure = { e ->
                    ErrorMessages.get_error("Logged In: ", "Logged in")
                }
            )
        }
    }

    fun getId(): Long {
        val id = sessionManager.getId()
        if (id != null){

            return id
        }
        return 0L
    }

    fun removeUser(){

        viewModelScope.launch {
            val result = uRepo.removeSelf(getId())
            Log.d("Remove Run ViewModel", "Running remove with id: ${getId()}")

            result.fold(
                onSuccess = {
                    sessionManager.clear()
                    "success"
                            },
                onFailure = { ErrorMessages.patch_error("User Settings", "Delete") }
            )
        }
    }

    private val _updateStatus = MutableLiveData<Boolean?>()
    val updateStatus: MutableLiveData<Boolean?> = _updateStatus
    fun patchUser(firstName: String, lastName: String, password: String, roles: List<RoleRequest>){

        viewModelScope.launch {
            request = UserRequestPatch(
                firstName = firstName,
                lastName = lastName,
                password = password,
                roles = roles
            )

            val result = uRepo.updateUser(request)
            result.fold(
                onSuccess = {
                    "success"
                       updateStatus.value = true     },
                onFailure = {
                    ErrorMessages.patch_error("User Settings", "Patch")
                    updateStatus.value = false
                }
            )
        }
    }




}
